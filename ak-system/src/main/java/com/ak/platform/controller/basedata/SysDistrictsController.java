package com.ak.platform.controller.basedata;

import com.ak.common.annotation.Log;
import com.ak.common.core.controller.BaseController;
import com.ak.common.core.domain.AjaxResult;
import com.ak.common.core.page.TableDataInfo;
import com.ak.common.enums.BusinessType;
import com.ak.common.utils.poi.ExcelUtil;
import com.ak.framework.util.ShiroUtils;
import com.ak.platform.domain.basedata.SysDistricts;
import com.ak.platform.service.basedata.ISysDistrictsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/basedata/districts")
public class SysDistrictsController extends BaseController {
    private String prefix = "basedata/districts";

    @Autowired
    private ISysDistrictsService districtsService;

    @RequiresPermissions("basedata:districts:view")
    @GetMapping()
    public String districts() {
        return prefix + "/districts";
    }

    @RequiresPermissions("basedata:districts:list")
    @GetMapping("demo")
    public String demo() {
        return prefix + "/demo";
    }

    /**
     * 查询地区列表
     */
    @RequiresPermissions("basedata:districts:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDistricts districts) {
        startPage();
        List<SysDistricts> list = districtsService.selectDistrictsList(districts);
        return getDataTable(list);
    }


    /**
     * 导出地区列表
     */
    @RequiresPermissions("basedata:districts:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDistricts districts) {
        List<SysDistricts> list = districtsService.selectDistrictsList(districts);
        ExcelUtil<SysDistricts> util = new ExcelUtil<SysDistricts>(SysDistricts.class);
        return util.exportExcel(list, "districts");
    }

    /**
     * 新增地区
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存地区
     */
    @RequiresPermissions("basedata:districts:add")
    @Log(title = "地区", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDistricts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setCreateTime(new Date());
        districts.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(districtsService.insertDistricts(districts));
    }

    /**
     * 修改地区
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap) {
        SysDistricts districts = districtsService.selectDistrictsById(id);
        mmap.put("districts", districts);
        return prefix + "/edit";
    }

    /**
     * 修改保存地区
     */
    @RequiresPermissions("basedata:districts:edit")
    @Log(title = "地区", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDistricts districts) {
        districts.setPid(districts.getId() / 100);
        districts.setUpdateBy(ShiroUtils.getLoginName());
        districts.setUpdateTime(new Date());
        return toAjax(districtsService.updateDistricts(districts));
    }

    /**
     * 删除地区
     */
    @RequiresPermissions("basedata:districts:remove")
    @Log(title = "地区", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(districtsService.deleteDistrictsByIds(ids));
    }
}