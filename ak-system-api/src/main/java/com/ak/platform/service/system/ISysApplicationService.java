package com.ak.platform.service.system;

import com.ak.platform.domain.system.SysApplication;

import java.util.List;

/**
 * 系统管理表
 */
public interface ISysApplicationService {

    /**
     * 分页查询系统信息
     *
     * @param application
     * @return
     */
    public List<SysApplication> selectApplicationList(SysApplication application);

    /**
     * 编辑
     *
     * @param application
     * @return
     */
    public int insertApplication(SysApplication application);

    /**
     * 校验编码是否唯一
     *
     * @param appCode
     * @return 结果
     */
    public int checkAppCodeUnique(String appCode);

    /**
     * 根据子系统编码查询
     *
     * @param appCode
     * @return
     */
    public SysApplication selectApplicationByappCode(String appCode);

    /**
     * 修改
     *
     * @param application
     * @return
     */
    public int updateApplication(SysApplication application);

    /**
     * 删除
     *
     * @param appCode
     * @return
     */
    public boolean deleteApplicationByCode(String appCode);

    /**
     * 获取所有可分配给租户的子系统  - 为租户分配子系统
     *
     * @return
     */
    List<SysApplication> selectApplicationsTenant();

    /**
     * 查询租户所有的子系统  - 为租户分配数据源
     *
     * @param tenantCode 租户编码
     * @return
     */
    List<SysApplication> selectSysApplicationsByTenantCode(String tenantCode);


    /**
     * TODO - 需要优化  租户建菜单怎么处理？
     * 获取所有在线的系统 -  菜单管理子系统
     *
     * @return
     */
    List<SysApplication> getSysApplications();
}
