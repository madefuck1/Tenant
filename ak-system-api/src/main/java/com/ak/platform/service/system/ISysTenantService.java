package com.ak.platform.service.system;

import com.ak.platform.domain.system.SysTenant;
import com.ak.platform.domain.system.SysTenantApplication;

import java.util.List;

/**
 * 租户业务层
 *
 * @author vean
 */
public interface ISysTenantService {

    /**
     * 根据条件分页查询租户数据
     *
     * @param tenant 租户信息
     * @return 租户数据集合信息
     */
    public List<SysTenant> selectTenantList(SysTenant tenant);

    /**
     * 通过租户编码删除租户
     *
     * @param tenantCode 租户编码
     * @return 结果
     */
    public boolean deleteTenantByCode(String tenantCode);

    /**
     * 新增保存租户信息
     *
     * @param tenant 租户信息
     * @return 结果
     */
    public int insertTenant(SysTenant tenant);

    /**
     * 修改保存租户信息
     *
     * @param tenant 租户信息
     * @return 结果
     */
    public int updateTenant(SysTenant tenant);

    /**
     * 通过主建编辑查询
     *
     * @param tenantCode
     * @return
     */
    public SysTenant selectTenantByTenantCode(String tenantCode);

    /**
     * 修改租户状态
     *
     * @param tenant
     * @return
     */
    public int changeStatus(SysTenant tenant);

    /**
     * 查询租户与子系统的关联
     *
     * @param tenantCode
     * @return
     */
    public List<SysTenantApplication> getTenantApplicationByTenantCode(String tenantCode);

    /**
     * 添加租户子与系统关联
     *
     * @param sysTenantApplication
     * @return
     */
    public int authApplication(SysTenantApplication sysTenantApplication);

}
