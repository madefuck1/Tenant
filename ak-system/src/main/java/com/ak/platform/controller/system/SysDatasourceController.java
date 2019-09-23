package com.ak.platform.controller.system;

import com.ak.common.annotation.Log;
import com.ak.common.core.controller.BaseController;
import com.ak.common.core.domain.AjaxResult;
import com.ak.common.core.page.TableDataInfo;
import com.ak.common.enums.BusinessType;
import com.ak.common.utils.CodeGenerateUtils;
import com.ak.framework.util.ShiroUtils;
import com.ak.platform.domain.system.SysApplication;
import com.ak.platform.domain.system.SysDatasource;
import com.ak.platform.domain.system.SysDatasourceApplication;
import com.ak.platform.domain.system.SysTenant;
import com.ak.platform.service.system.ISysApplicationService;
import com.ak.platform.service.system.ISysDatasourceService;
import com.ak.platform.service.system.ISysTenantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据源管理
 *
 * @author Vean
 * @date 2019-06-23
 */
@Controller
@RequestMapping("/system/datasource")
public class SysDatasourceController extends BaseController {

    private String prefix = "system/datasource";

    @Resource
    private ISysDatasourceService dataSourceService;

    @Resource
    private ISysTenantService tenantService;

    @Resource
    private ISysApplicationService applicationService;

    @RequiresPermissions("system:datasource:view")
    @GetMapping()
    public String datasource(ModelMap mmap) {
        mmap.put("tenants", tenantService.selectTenantList(new SysTenant()));
        return prefix + "/datasource";
    }

    /**
     * 查询数据源列表
     */
    @RequiresPermissions("system:datasource:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDatasource datasource) {
        startPage();
        List<SysDatasource> list = dataSourceService.selectDatasourceList(datasource);
        return getDataTable(list);
    }

    /**
     * 新增数据源
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("datasourceCode", this.getUniqueDatasourceCode());
        mmap.put("tenants", tenantService.selectTenantList(new SysTenant()));
        return prefix + "/add";
    }

    /**
     * 新增保存数据源
     */
    @RequiresPermissions("system:datasource:add")
    @Log(title = "新增数据源", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDatasource datasource) {
        datasource.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(dataSourceService.insertDatasource(datasource));
    }

    /**
     * 修改数据源
     */
    @GetMapping("/edit/{datasourceCode}")
    public String edit(@PathVariable("datasourceCode") String datasourceCode, ModelMap mmap) {
        mmap.put("datasource", dataSourceService.selectByDatasourceCode(datasourceCode));
        mmap.put("tenants", tenantService.selectTenantList(new SysTenant()));
        return prefix + "/edit";
    }

    /**
     * 修改保存数据源
     */
    @RequiresPermissions("system:datasource:edit")
    @Log(title = "修改数据源", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDatasource datasource) {
        datasource.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(dataSourceService.updateDatasource(datasource));
    }

    /**
     * 删除数据源
     */
    @RequiresPermissions("system:datasource:remove")
    @Log(title = "删除数据源", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dataSourceService.deleteDatasourceByCodes(ids));
    }

    /**
     * 用户状态修改
     */
    @Log(title = "修改数据源状态", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:datasource:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysDatasource datasource) {
        return toAjax(dataSourceService.changeStatus(datasource));
    }

    private String getUniqueDatasourceCode() {
        String datasourceCode = CodeGenerateUtils.datasourceCodeGenerate();
        if (dataSourceService.checkDatasourceCodeUnique(datasourceCode) > 0) {
            getUniqueDatasourceCode();
        }
        return datasourceCode;
    }


    /**
     * 分配子系统
     *
     * @param datasourceCode
     * @param mmap
     * @return
     */
    @GetMapping("/setApplication/{datasourceCode}")
    public String setApplication(@PathVariable("datasourceCode") String datasourceCode, ModelMap mmap) {
        SysDatasource sd = dataSourceService.selectByDatasourceCode(datasourceCode);
        // 获取租户所有的子系统
        List<SysApplication> applications = applicationService.selectSysApplicationsByTenantCode(sd.getTenantCode());
        StringBuffer appCodesOld = new StringBuffer("");
        if (null != applications && applications.size() > 0) {
            // 判断哪些是选中状态
            List<SysDatasourceApplication> datasourceApplicationList = dataSourceService.getDatasourceApplicationByDatasourceCode(datasourceCode);
            if (null != datasourceApplicationList && datasourceApplicationList.size() > 0) {
                for (SysDatasourceApplication datasourceApplication : datasourceApplicationList) {
                    appCodesOld.append(datasourceApplication.getAppCode()).append(",");
                    for (SysApplication application : applications) {
                        if (application.getAppCode().equals(datasourceApplication.getAppCode())) {
                            application.setFlag(true);
                        }
                    }
                }
            }
        }
        mmap.put("datasourceCode", datasourceCode);
        mmap.put("applications", applications);
        mmap.put("appCodesOld", appCodesOld.toString());
        return prefix + "/setApplication";
    }

    /**
     * 保存租户子系统
     */
    @RequiresPermissions("system:datasource:edit")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/setApplication")
    @ResponseBody
    public AjaxResult setApplication(@RequestBody SysDatasourceApplication datasourceApplication) {
        dataSourceService.setDatasourceApplication(datasourceApplication);
        return success();
    }
}
