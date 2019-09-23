package com.ak.common.enums;

/**
 * 子系统枚举类
 * 开发子系统之前，先在此文件中定义好子系统编码，
 * 子系统管理中的系统编码从这里读取过去的
 *
 * @author Vean
 * @date 2019-08-01
 */
public enum TenantApplication {

    PLATFORM_TENANT_APPCODE("PlatformTenant", "平台租户管理"),
    PLATFORM_MONITOR_APPCODE("PlatformMonitor", "统一监控子系统"),
    PLATFORM_QUARTZ_APPCODE("PlatformQuartz", "任务调度子系统"),

    /** ===== 以上是平台级别的模块，租户不可使用 ===== **/

    /**
     * ===== 以下是租户可使用的子系统 =====
     **/

    TENANT_BASEDATE_APPCODE("TenantBasedate", "基础数据子系统"),
    TENANT_MESSAGE_APPCODE("TenantMessage", "消息中心子系统"),
    TENANT_LOG_APPCODE("TenantLog", "日志查询子系统"),
    TENANT_API_APPCODE("TenantApi", "平台开放接口");

    private final String code;
    private final String info;

    TenantApplication(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
