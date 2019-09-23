package com.ak.platform.mapper.system;

import com.ak.platform.domain.system.SysDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Vean
 */
@Mapper
public interface SysDatasourceMapper {

    /**
     * 查询租户数据源
     *
     * @param tenantCode
     * @return
     */
    SysDatasource queryByTenant(@Param("tenantCode") String tenantCode);

    /**
     * 根据数据源编码获取数据源
     *
     * @param datasourceCode
     * @return 数据源信息
     */
    public SysDatasource selectByDatasourceCode(String datasourceCode);

    /**
     * 查询数据源列表
     *
     * @param datasource 数据源
     * @return 数据源集合
     */
    public List<SysDatasource> selectDatasourceList(SysDatasource datasource);

    /**
     * 新增数据源
     *
     * @param datasource 数据源信息
     * @return 结果
     */
    public int insertDatasource(SysDatasource datasource);

    /**
     * 修改数据源
     *
     * @param datasource 数据源
     * @return 结果
     */
    public int updateDatasource(SysDatasource datasource);

    /**
     * 删除数据源
     *
     * @param datasourceCodes 数据源编码
     * @return 结果
     */
    public int deleteDatasourceByCodes(String[] datasourceCodes);

    /**
     * 验证编码是否存在
     *
     * @param datasourceCode
     * @return
     */
    public int checkDatasourceCodeUnique(String datasourceCode);

}
