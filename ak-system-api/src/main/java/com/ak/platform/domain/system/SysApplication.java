package com.ak.platform.domain.system;

import com.ak.common.annotation.Excel;
import com.ak.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子系统表 sys_application
 *
 * @author Vean
 */
@Getter
@Setter
public class SysApplication extends BaseEntity {

    /**
     * 子系统编码  编码规则：由6位数字组成 CodeGenerateUtils.applicationCodeGenerate()
     */
    @NotBlank(message = "系统编码不能为空")
    @Excel(name = "系统编码", prompt = "系统编码")
    private String appCode;

    /**
     * 子系统名称
     */
    @NotBlank(message = "子系统名称不能为空")
    @Excel(name = "子系统名称")
    private String appName;

    /**
     * 子系统访问地址
     */
    @NotBlank(message = "子系统访问地址不能为空")
    @Excel(name = "子系统访问地址")
    private String appUri;

    /**
     * 子系统类型
     */
    private String appType;

    /**
     * 子系统在服务器上的部署路径
     */
    @Excel(name = "子系统部署路径")
    private String appAddress;

    /**
     * 部门状态:0正常,1停用
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 显示顺序
     */
    private Long sort;

    /**
     * 租户是否存在此子系统标识 默认不存在
     */
    private boolean flag = false;


}
