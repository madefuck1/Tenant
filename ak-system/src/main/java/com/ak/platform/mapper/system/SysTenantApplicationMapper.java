package com.ak.platform.mapper.system;

import com.ak.platform.domain.system.SysTenantApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租户关联系统表
 */
public interface SysTenantApplicationMapper {

    /**
     * 根据租户编码查询子系统关联
     *
     * @param tenantCode
     * @return
     */
    public List<SysTenantApplication> getTenantApplicationByTenantCode(String tenantCode);

    /**
     * 根据租户编码删除子系统关联
     *
     * @param tenantCode
     * @return
     */
    public Boolean deleteTenantApplicationByTenantCode(@Param("tenantCode") String tenantCode, @Param("appCode") String appCode);

    /**
     * 添加租户关联子系统
     *
     * @param tenantCode
     * @param appCodes
     * @return
     */
    public int batchTenantApplication(@Param("tenantCode") String tenantCode, @Param("appCodes") List<String> appCodes);

}
