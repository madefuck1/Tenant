package com.ak.platform.controller.system;

import com.ak.common.annotation.Log;
import com.ak.common.core.controller.BaseController;
import com.ak.common.core.domain.AjaxResult;
import com.ak.common.core.page.TableDataInfo;
import com.ak.common.enums.BusinessType;
import com.ak.common.utils.poi.ExcelUtil;
import com.ak.framework.util.ShiroUtils;
import com.ak.platform.domain.system.SysApplication;
import com.ak.platform.service.system.ISysApplicationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Vean
 */
@Controller
@RequestMapping("/system/application")
public class SysApplicationController extends BaseController {

    private String prefix = "system/application";

    @Autowired
    private ISysApplicationService applicationService;

    @RequiresPermissions("system:application:view")
    @GetMapping()
    public String application() {
        return prefix + "/application";
    }

    /**
     * 系统管理列表
     *
     * @param application
     * @return
     */
    @RequiresPermissions("system:application:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysApplication application) {
        startPage();
        List<SysApplication> list = applicationService.selectApplicationList(application);
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
        return prefix + "/add";
    }

    /**
     * 添加系统数据
     *
     * @param application
     * @return
     */
    @RequiresPermissions("system:application:add")
    @Log(title = "添加系统", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysApplication application) {
        application.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(applicationService.insertApplication(application));
    }


    /**
     * 校验编码
     */
    @PostMapping("/checkAppCodeUnique")
    @ResponseBody
    public String checkAppCodeUnique(SysApplication application) {
        return String.valueOf(applicationService.checkAppCodeUnique(application.getAppCode()));
    }

    /**
     * 修改系统页面
     */
    @GetMapping("/edit/{appCode}")
    public String edit(@PathVariable("appCode") String appCode, ModelMap mmap) {
        mmap.put("applications", applicationService.selectApplicationByappCode(appCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存系统信息
     */
    @Log(title = "修改系统信息", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:application:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysApplication application) {
        //获取登录用户信息
        application.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(applicationService.updateApplication(application));
    }

    /**
     * 删除系统数据
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:application:remove")
    @Log(title = "删除系统", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(applicationService.deleteApplicationByCode(ids));
    }

    /**
     * 系统状态修改
     */
    @Log(title = "修改系统状态", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:application:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysApplication application) {
        //获取登录用户信息
        application.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(applicationService.updateApplication(application));
    }

    /**
     * 导出数据
     *
     * @param application
     * @return
     */
    @Log(title = "系统数据导出", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:application:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysApplication application) {
        List<SysApplication> list = applicationService.selectApplicationList(application);
        ExcelUtil<SysApplication> util = new ExcelUtil<SysApplication>(SysApplication.class);
        return util.exportExcel(list, "租户数据");
    }

}
