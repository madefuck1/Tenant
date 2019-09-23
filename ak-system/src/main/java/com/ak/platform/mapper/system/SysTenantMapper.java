package com.ak.platform.mapper.system;

import com.ak.platform.domain.system.SysTenant;

import java.util.List;

/**
 * 租户表表 数据层
 *
 * @author Vean
 */
public interface SysTenantMapper {

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
     * 根据主建ID查询
     *
     * @param tenantCode
     * @return
     */
    public SysTenant selectTenantByTenantCode(String tenantCode);

}
