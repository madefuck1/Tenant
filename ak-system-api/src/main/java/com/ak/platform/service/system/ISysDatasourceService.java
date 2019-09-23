package com.ak.platform.service.system;

import com.ak.platform.domain.system.SysDatasource;
import com.ak.platform.domain.system.SysDatasourceApplication;

import java.util.List;

/**
 * 数据源业务
 *
 * @author Vean
 */
public interface ISysDatasourceService {

    /**
     * 根据租户编码，子系统编码，数据源类型获取数据源
     *
     * @param tenantCode
     * @return
     */
    SysDatasource queryByTenant(String tenantCode);

    /**
     * 根据数据源编码获取数据源
     *
     * @param datasourceCode
     * @return 数据源信息
     */
    SysDatasource selectByDatasourceCode(String datasourceCode);

    /**
     * 查询数据源列表
     *
     * @param datasource 数据源
     * @return 数据源集合
     */
    List<SysDatasource> selectDatasourceList(SysDatasource datasource);

    /**
     * 新增数据源
     *
     * @param datasource 数据源信息
     * @return 结果
     */
    int insertDatasource(SysDatasource datasource);

    /**
     * 修改数据源
     *
     * @param datasource 数据源
     * @return 结果
     */
    int updateDatasource(SysDatasource datasource);

    /**
     * 删除数据源
     *
     * @param datasourceCodes 数据源编码
     * @return 结果
     */
    int deleteDatasourceByCodes(String datasourceCodes);

    /**
     * 修改数据源状态
     *
     * @param datasource 数据源
     * @return 结果
     */
    int changeStatus(SysDatasource datasource);

    /**
     * 验证编码是否存在
     *
     * @param datasourceCode
     * @return
     */
    int checkDatasourceCodeUnique(String datasourceCode);

    /**
     * 获取数据源与子系统的关联
     *
     * @param datasourceCode
     * @return
     */
    List<SysDatasourceApplication> getDatasourceApplicationByDatasourceCode(String datasourceCode);

    /**
     * 添加数据源与子系统关联
     *
     * @param datasourceApplication
     * @return
     */
    public void setDatasourceApplication(SysDatasourceApplication datasourceApplication);
}
