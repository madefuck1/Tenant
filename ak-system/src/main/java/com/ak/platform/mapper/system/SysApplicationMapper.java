package com.ak.platform.mapper.system;


import com.ak.platform.domain.system.SysApplication;

import java.util.List;

/**
 * 系统管理数据层
 */

public interface SysApplicationMapper {
    /**
     * 列表分页
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
     * 修改
     *
     * @param application
     * @return
     */
    public int updateApplication(SysApplication application);

    /**
     * 根据主建编码查询
     *
     * @param appCode
     * @return
     */
    public SysApplication selectSysApplicationByAppCode(String appCode);

    /**
     * 根据主建ID删除
     *
     * @param appCode
     * @return
     */
    public boolean deleteApplicationByCode(String appCode);

    public int checkAppCodeUnique(String appCode);

    public List<SysApplication> selectApplicationsByTenantCode(String tenantCode);

    List<SysApplication> selectApplicationsTenant();


    public List<SysApplication> getSysApplications();
}
