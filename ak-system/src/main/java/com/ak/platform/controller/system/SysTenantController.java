package com.ak.platform.controller.system;

import com.ak.common.annotation.Log;
import com.ak.common.core.controller.BaseController;
import com.ak.common.core.domain.AjaxResult;
import com.ak.common.core.page.TableDataInfo;
import com.ak.common.enums.BusinessType;
import com.ak.common.utils.CodeGenerateUtils;
import com.ak.common.utils.poi.ExcelUtil;
import com.ak.framework.util.ShiroUtils;
import com.ak.platform.domain.system.SysApplication;
import com.ak.platform.domain.system.SysTenant;
import com.ak.platform.domain.system.SysTenantApplication;
import com.ak.platform.service.system.ISysApplicationService;
import com.ak.platform.service.system.ISysTenantService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户管理
 *
 * @author Vean
 */
@Controller
@RequestMapping("/system/tenant")
public class SysTenantController extends BaseController {

    private String prefix = "system/tenant";

    @Autowired
    private ISysTenantService tenantService;

    @Autowired
    private ISysApplicationService applicationService;

    @RequiresPermissions("system:tenant:view")
    @GetMapping()
    public String role() {
        return prefix + "/tenant";
    }

    @RequiresPermissions("system:tenant:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysTenant tenant) {
        startPage();
        List<SysTenant> list = tenantService.selectTenantList(tenant);
        return getDataTable(list);
    }

    /**
     * 新增页面
     *
     * @param mmap
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap mmap) {
        mmap.put("tenantCode", getUniqueTenantCode());
        return prefix + "/add";
    }

    /**
     * 获取唯一编码
     *
     * @return
     */
    private String getUniqueTenantCode() {
        String tenantCode = CodeGenerateUtils.tenantCodeGenerate();
        if (tenantService.selectTenantByTenantCode(tenantCode) != null) {
            getUniqueTenantCode();
        }
        return tenantCode;
    }

    /**
     * 添加租户数据
     *
     * @param tenant
     * @return
     */
    @RequiresPermissions("system:tenant:add")
    @Log(title = "添加租户", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysTenant tenant) {
        tenant.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(tenantService.insertTenant(tenant));
    }


    /**
     * 修改租户
     */
    @GetMapping("/edit/{tenantCode}")
    public String edit(@PathVariable("tenantCode") String tenantCode, ModelMap mmap) {
        mmap.put("tenant", tenantService.selectTenantByTenantCode(tenantCode));
        return prefix + "/edit";
    }


    /**
     * 修改保存租户信息
     */
    @Log(title = "修改租户信息", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:tenant:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysTenant tenant) {
        // 获取登录用户信息
        tenant.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(tenantService.updateTenant(tenant));
    }

    /**
     * 删除租户数据
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:tenant:remove")
    @Log(title = "删除租户", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(tenantService.deleteTenantByCode(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 租户状态修改
     */
    @Log(title = "租户状态修改", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:tenant:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysTenant tenant) {
        //获取登录用户信息
        tenant.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(tenantService.changeStatus(tenant));
    }

    /**
     * 导出数据
     *
     * @param tenant
     * @return
     */
    @Log(title = "租户数据导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:tenant:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysTenant tenant) {
        List<SysTenant> list = tenantService.selectTenantList(tenant);
        ExcelUtil<SysTenant> util = new ExcelUtil<SysTenant>(SysTenant.class);
        return util.exportExcel(list, "租户数据");
    }

    /**
     * 分配子系统
     *
     * @param tenantCode
     * @param mmap
     * @return
     */
    @GetMapping("/authApplication/{tenantCode}")
    public String authApplication(@PathVariable("tenantCode") String tenantCode, ModelMap mmap) {
        mmap.put("tenant", tenantService.selectTenantByTenantCode(tenantCode));
        // 获取所有可分配给租户的子系统
        List<SysApplication> applications = applicationService.selectApplicationsTenant();
        if (applications != null && applications.size() > 0) {
            // 判断哪些是选中状态
            List<SysTenantApplication> sysTenantApplications = tenantService.getTenantApplicationByTenantCode(tenantCode);
            for (SysTenantApplication tenantApplication : sysTenantApplications) {
                for (SysApplication sysApplication : applications) {
                    if (sysApplication.getAppCode().equals(tenantApplication.getAppCode())) {
                        sysApplication.setFlag(true);
                    }
                }
            }
            //mmap.put("sysTenantApplications",sysTenantApplications);
        }
        mmap.put("applications", applications);
        return prefix + "/authApplication";
    }

    /**
     * 保存租户子系统
     */
    @RequiresPermissions("system:tenant:edit")
    @Log(title = "租户分配子系统", businessType = BusinessType.UPDATE)
    @PostMapping("/authApplication")
    @ResponseBody
    public AjaxResult authApplication(@RequestBody SysTenantApplication sysTenantApplication) {
        tenantService.authApplication(sysTenantApplication);
        return success();
    }
}
