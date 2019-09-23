package com.ak.platform.service.system;

import com.ak.platform.domain.system.SysApplication;
import com.ak.platform.mapper.system.SysApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统管理
 */
@Service
public class SysApplicationServiceImpl implements ISysApplicationService {

    @Autowired
    private SysApplicationMapper sysApplicationMapper;

    /**
     * 系统列表
     *
     * @param application
     * @return
     */
    @Override
    public List<SysApplication> selectApplicationList(SysApplication application) {
        return sysApplicationMapper.selectApplicationList(application);
    }

    /**
     * 根据主建ID查询
     *
     * @param appCode
     * @return
     */
    @Override
    public SysApplication selectApplicationByappCode(String appCode) {
        return sysApplicationMapper.selectSysApplicationByAppCode(appCode);
    }

    /**
     * 根据主建编码删除
     *
     * @param appCode
     * @return
     */
    @Override
    public boolean deleteApplicationByCode(String appCode) {
        SysApplication aplication = new SysApplication();
        aplication.setAppCode(appCode);
        aplication.setDelFlag("2");
        int num = sysApplicationMapper.updateApplication(aplication);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int insertApplication(SysApplication application) {
        return sysApplicationMapper.insertApplication(application);
    }

    @Override
    public int updateApplication(SysApplication application) {
        return sysApplicationMapper.updateApplication(application);
    }

    @Override
    public int checkAppCodeUnique(String appCode) {
        return sysApplicationMapper.checkAppCodeUnique(appCode);
    }

    @Override
    public List<SysApplication> selectSysApplicationsByTenantCode(String tenantCode) {
        return sysApplicationMapper.selectApplicationsByTenantCode(tenantCode);
    }

    @Override
    public List<SysApplication> selectApplicationsTenant() {
        return sysApplicationMapper.selectApplicationsTenant();
    }


    @Override
    public List<SysApplication> getSysApplications() {
        return sysApplicationMapper.getSysApplications();
    }
}
