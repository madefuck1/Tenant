package com.ak.platform.service.system;

import com.ak.common.core.text.Convert;
import com.ak.platform.domain.system.SysDatasource;
import com.ak.platform.domain.system.SysDatasourceApplication;
import com.ak.platform.mapper.system.SysDatasourceApplicationMapper;
import com.ak.platform.mapper.system.SysDatasourceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据源业务实现
 *
 * @author Vean
 */
@Service
public class SysDatasourceServiceImpl implements ISysDatasourceService {

    @Resource
    private SysDatasourceMapper dataSourceMapper;

    @Resource
    private SysDatasourceApplicationMapper datasourceApplicationMapper;

    @Override
    public SysDatasource queryByTenant(String tenantCode) {
        return dataSourceMapper.queryByTenant(tenantCode);
    }

    @Override
    public SysDatasource selectByDatasourceCode(String datasourceCode) {
        return dataSourceMapper.selectByDatasourceCode(datasourceCode);
    }

    @Override
    public List<SysDatasource> selectDatasourceList(SysDatasource datasource) {
        return dataSourceMapper.selectDatasourceList(datasource);
    }

    @Override
    public int insertDatasource(SysDatasource datasource) {
        return dataSourceMapper.insertDatasource(datasource);
    }

    @Override
    public int updateDatasource(SysDatasource datasource) {
        return dataSourceMapper.updateDatasource(datasource);
    }

    @Override
    public int deleteDatasourceByCodes(String datasourceCodes) {
        return dataSourceMapper.deleteDatasourceByCodes(Convert.toStrArray(datasourceCodes));
    }

    /**
     * 修改数据源状态
     *
     * @param datasource 数据源
     * @return 结果
     */
    @Override
    public int changeStatus(SysDatasource datasource) {
        return dataSourceMapper.updateDatasource(datasource);
    }

    @Override
    public int checkDatasourceCodeUnique(String datasourceCode) {
        return dataSourceMapper.checkDatasourceCodeUnique(datasourceCode);
    }

    @Override
    public List<SysDatasourceApplication> getDatasourceApplicationByDatasourceCode(String datasourceCode) {
        return datasourceApplicationMapper.getDatasourceApplicationByDatasourceCode(datasourceCode);
    }

    @Override
    public void setDatasourceApplication(SysDatasourceApplication datasourceApplication) {
        List<String> appCodesNew = datasourceApplication.getAppCodesNew();
        List<String> appCodesOld = null;
        if (!datasourceApplication.getAppCodesOld().isEmpty()) {
            appCodesOld = Arrays.asList(datasourceApplication.getAppCodesOld().trim().split(","));
        }
        // 需要新增的
        if (null != appCodesNew && appCodesNew.size() > 0) {
            List<String> appCodesInsert = new ArrayList<String>();
            appCodesInsert.addAll(appCodesNew);
            if (null != appCodesOld) {
                appCodesInsert.removeAll(appCodesOld);
            }
            List<SysDatasourceApplication> insertDatasourceApplications = new ArrayList<SysDatasourceApplication>();
            appCodesInsert.forEach(s -> {
                SysDatasourceApplication sda = new SysDatasourceApplication();
                sda.setDatasourceCode(datasourceApplication.getDatasourceCode());
                sda.setAppCode(s);
                insertDatasourceApplications.add(sda);
            });
            datasourceApplicationMapper.batchDatasourceApplication(insertDatasourceApplications);
        }
        // 需求删除的
        if (null != appCodesOld && appCodesOld.size() > 0) {
            List<String> appCodesDelete = new ArrayList<String>();
            appCodesDelete.addAll(appCodesOld);
            appCodesDelete.removeAll(appCodesNew);
            appCodesDelete.forEach(s -> {
                SysDatasourceApplication sda = new SysDatasourceApplication();
                sda.setDatasourceCode(datasourceApplication.getDatasourceCode());
                sda.setAppCode(s);
                datasourceApplicationMapper.deleteDatasourceApplication(sda);
            });
        }
    }
}
