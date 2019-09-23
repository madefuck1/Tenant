package com.ak.utils;

import com.ak.common.config.Global;
import com.ak.common.exception.BusinessException;
import com.ak.common.utils.spring.SpringUtils;
import com.ak.framework.datasource.DataSourceBeanBuilder;
import com.ak.framework.datasource.DataSourceHolder;
import com.ak.framework.util.ShiroUtils;
import com.ak.platform.domain.system.SysDatasource;
import com.ak.platform.mapper.system.SysDatasourceMapper;
import com.ak.platform.service.system.ISysDatasourceService;

/**
 * @author Vean
 */
public class CageDataSource {

    public static void action(SysDatasourceMapper dataSourceMapper) throws BusinessException {

        if (null != ShiroUtils.getSysUser() && !Global.DEFAULT_TENANT_CODE.equals(ShiroUtils.getSysUser().getTenantCode())) {
            SysDatasource _dataSource_ = dataSourceMapper.queryByTenant(ShiroUtils.getSysUser().getTenantCode());
            if (_dataSource_ != null) {
                DataSourceBeanBuilder builder = new DataSourceBeanBuilder(_dataSource_);
                DataSourceHolder.setDataSource(builder);
            } else {
                throw new RuntimeException("获取数据源信息异常，请检查租户 [" + ShiroUtils.getSysUser().getTenantCode() + "] 的数据源配置");
            }
        }
    }
}
