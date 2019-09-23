package com.ak.platform.domain.system;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 租户子系统中间表 sys_tenant_application
 *
 * @author Vean
 */
@Data
public class SysTenantApplication {

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 子系统编码
     */
    private String appCode;

    @NotBlank(message = "系统不能为空")
    private List<String> appCodes;

}
