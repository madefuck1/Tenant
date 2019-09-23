package com.ak.platform.service.system;

import com.ak.platform.domain.basedata.*;
import com.ak.platform.domain.system.SysTenant;
import com.ak.platform.domain.system.SysTenantApplication;
import com.ak.platform.mapper.basedata.*;
import com.ak.platform.mapper.system.SysTenantApplicationMapper;
import com.ak.platform.mapper.system.SysTenantMapper;
import com.ak.platform.service.basedata.ISysDeptService;
import com.ak.platform.service.basedata.ISysRoleService;
import com.ak.platform.service.basedata.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户 业务层处理
 *
 * @author Vean
 */
@Service
public class SysTenantServiceImpl implements ISysTenantService {

    @Autowired
    private SysTenantMapper tenantMapper;
    @Autowired
    private SysTenantApplicationMapper tenantApplicationMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysRoleDeptMapper roleDeptMapper;
    @Autowired
    private SysMenuMapper menuMapper;
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    //service
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysRoleService roleService;
    @Autowired
    private ISysDeptService deptService;


    @Override
    public List<SysTenant> selectTenantList(SysTenant tenant) {
        return tenantMapper.selectTenantList(tenant);
    }

    @Override
    public boolean deleteTenantByCode(String tenantCode) {
        SysTenant tenant = new SysTenant();
        tenant.setTenantCode(tenantCode);
        tenant.setDelFlag("2");//逻辑删除
        //改状态0
        int num = tenantMapper.updateTenant(tenant);
        if (num > 0) {
            //删除该租户下的所有关联系统表
            tenantApplicationMapper.deleteTenantApplicationByTenantCode(tenantCode, null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加租户信息
     *
     * @param tenant 租户信息
     * @return
     */
    @Override
    public int insertTenant(SysTenant tenant) {
        return tenantMapper.insertTenant(tenant);
    }

    /**
     * 修改租户信息
     *
     * @param tenant 租户信息
     * @return
     */
    @Override
    public int updateTenant(SysTenant tenant) {
        return tenantMapper.updateTenant(tenant);
    }

    /**
     * 根据主建编辑查询租户信息
     *
     * @param tenantCode
     * @return
     */
    @Override
    public SysTenant selectTenantByTenantCode(String tenantCode) {
        return tenantMapper.selectTenantByTenantCode(tenantCode);
    }

    /**
     * 修改租户状态
     *
     * @param tenant
     * @return
     */
    @Override
    public int changeStatus(SysTenant tenant) {
        return tenantMapper.updateTenant(tenant);
    }

    /**
     * 租户分配子系统
     *
     * @param tenantApplication
     * @return
     */
    @Override
    public int authApplication(SysTenantApplication tenantApplication) {
        //查询该租户信息
        SysTenant tenant = tenantMapper.selectTenantByTenantCode(tenantApplication.getTenantCode());
        if (tenant == null) {
            return 0;
        }
        //先查询该租户是否分配了子系统
        List<SysTenantApplication> listist = tenantApplicationMapper.getTenantApplicationByTenantCode(tenantApplication.getTenantCode());
        //查询出该租户下的角色
        List<SysRole> sysRoles = roleMapper.getSysRoleByTenantCode(tenantApplication.getTenantCode());
        if (listist != null && listist.size() > 0) {
            //存新增的
            List<String> newAppCodes = new ArrayList<String>();
            //存废除的
            List<String> oldAppCodes = new ArrayList<String>();
            //获取新的
            for (String appCode : tenantApplication.getAppCodes()) {
                SysTenantApplication SysTenantApplicationnew = new SysTenantApplication();
                SysTenantApplicationnew.setAppCode(appCode);
                SysTenantApplicationnew.setTenantCode(tenantApplication.getTenantCode());
                Boolean flge = listist.contains(SysTenantApplicationnew);
                if (!flge) {
                    newAppCodes.add(appCode);
                }
            }
            //获取废除事物
            for (SysTenantApplication sysTenantApplication : listist) {
                Boolean flge = tenantApplication.getAppCodes().contains(sysTenantApplication.getAppCode());
                if (!flge) {
                    oldAppCodes.add(sysTenantApplication.getAppCode());
                }
            }
            //清空旧的角色关联的菜单和关联租户
            if (oldAppCodes != null && oldAppCodes.size() > 0) {
                deleteRoleMensTenantOld(tenantApplication.getTenantCode(), oldAppCodes, sysRoles);
            }
            //编辑时添加新的系统关联信息
            if (newAppCodes != null && newAppCodes.size() > 0) {
                //添加关联租户关联系统
                tenantApplicationMapper.batchTenantApplication(tenantApplication.getTenantCode(), newAppCodes);
                //添加角色关联的菜单
                insertMenuRoles(newAppCodes, sysRoles);
            }
        } else {
            //只在第一次分配系统时创建
            //添加关联租户关联系统
            tenantApplicationMapper.batchTenantApplication(tenantApplication.getTenantCode(), tenantApplication.getAppCodes());
            //创建部门
            SysDept dept = deptService.insertSysDeptDefault(tenant);
            //创建角色
            SysRole role = roleService.insertSysRoleDefault(tenant);
            //创建角色关联部门
            List<SysRoleDept> roleDeptList = getSysRoleDepts(dept, role);
            roleDeptMapper.batchRoleDept(roleDeptList);
            //添加角色关联菜单
            List<SysRole> roles = new ArrayList<>();
            roles.add(role);
            insertMenuRoles(tenantApplication.getAppCodes(), roles);
            //创建用户
            SysUser user = userService.insertUserDefault(tenant, dept);
            //添加用户归属角色
            List<SysUserRole> userRoles = getSysUserRoles(role, user);
            userRoleMapper.batchUserRole(userRoles);
        }
        return 1;
    }

    private List<SysRoleDept> getSysRoleDepts(SysDept dept, SysRole role) {
        List<SysRoleDept> roleDeptList = new ArrayList<>();
        SysRoleDept roleDept = new SysRoleDept();
        roleDept.setDeptId(dept.getDeptId());
        roleDept.setRoleId(role.getRoleId());
        roleDeptList.add(roleDept);
        return roleDeptList;
    }

    private List<SysUserRole> getSysUserRoles(SysRole role, SysUser user) {
        List<SysUserRole> userRoles = new ArrayList<>();
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(role.getRoleId());
        userRole.setUserId(user.getUserId());
        userRoles.add(userRole);
        return userRoles;
    }

    /**
     * 根据系统添加角色与菜单的关联
     *
     * @param appCodes
     * @param roles
     */
    private void insertMenuRoles(List<String> appCodes, List<SysRole> roles) {
        for (String appCode : appCodes) {
            List<SysMenu> sysMenus = menuMapper.setectMenuByAppCode(appCode);
            if (sysMenus != null && sysMenus.size() > 0 && roles != null && roles.size() > 0) {
                batchRolesmenus(roles, sysMenus);
            }
        }
    }

    private void batchRolesmenus(List<SysRole> roles, List<SysMenu> sysMenus) {
        List<SysRoleMenu> sysRoleMenus = new ArrayList();
        for (SysRole role : roles) {
            for (SysMenu menu : sysMenus) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menu.getMenuId());
                sysRoleMenu.setRoleId(role.getRoleId());
                sysRoleMenus.add(sysRoleMenu);
            }
        }
        roleMenuMapper.batchRoleMenu(sysRoleMenus);
    }

    /**
     * 删除角色跟菜单的关联
     *
     * @param tenantCode
     * @param appCodes
     * @param sysRoles
     */
    private void deleteRoleMensTenantOld(String tenantCode, List<String> appCodes, List<SysRole> sysRoles) {
        for (String appCode : appCodes) {
            tenantApplicationMapper.deleteTenantApplicationByTenantCode(tenantCode, appCode);
            List<SysMenu> sysMenus = menuMapper.setectMenuByAppCode(appCode);
            if (sysMenus != null && sysMenus.size() > 0 && sysRoles != null && sysRoles.size() > 0) {
                for (SysRole role : sysRoles) {
                    roleMenuMapper.deleteRoleMenusAndRoleId(sysMenus, role.getRoleId());
                }
            }
        }
    }

    @Override
    public List<SysTenantApplication> getTenantApplicationByTenantCode(String tenantCode) {
        return tenantApplicationMapper.getTenantApplicationByTenantCode(tenantCode);
    }
}
