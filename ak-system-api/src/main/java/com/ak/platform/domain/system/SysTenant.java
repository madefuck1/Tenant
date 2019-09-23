package com.ak.platform.domain.system;

import com.ak.common.annotation.Excel;
import com.ak.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 租户信息表 sys_tenant
 *
 * @author Vean
 */
@Getter
@Setter
public class SysTenant extends BaseEntity {

    /**
     * 租户编码 编码规则：由8位大写母组成 CodeUtils.tenantCodeGenerate()
     */
    @NotBlank(message = "租户编码不能为空")
    @Excel(name = "租户编码", prompt = "租户编码")
    private String tenantCode;

    /**
     * 租户名称
     */
    @Excel(name = "租户名称")
    @NotBlank(message = "租户名称不能为空")
    private String tenantName;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    @NotBlank(message = "联系人不能为空")
    private String contactMan;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|(18[0-9])|(19[89]))\\d{8})$", message = "手机号格式错误")
    private String contactNumber;

    /**
     * 联系地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 部门状态:0正常,1停用
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /* ======= 以上是表字段，下面是数据关系 ======= */

    /**
     * 租户选购的子系统
     */
    private String[] appCodes;
}
