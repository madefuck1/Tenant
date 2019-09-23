package com.ak.platform.mapper.system;

import com.ak.platform.domain.system.SysDatasourceApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源关联子系统表
 */
@Mapper
public interface SysDatasourceApplicationMapper {

    /**
     * 获取数据源对应的子系统
     *
     * @param datasourceCode
     * @return
     */
    public List<SysDatasourceApplication> getDatasourceApplicationByDatasourceCode(String datasourceCode);

    /**
     * 添加数据源子系统关联
     *
     * @param datasourceApplications
     * @return
     */
    public int batchDatasourceApplication(@Param("datasourceApplications") List<SysDatasourceApplication> datasourceApplications);

    /**
     * 删除数据源子系统关联
     *
     * @param datasourceApplication
     * @return
     */
    public int deleteDatasourceApplication(SysDatasourceApplication datasourceApplication);

}
