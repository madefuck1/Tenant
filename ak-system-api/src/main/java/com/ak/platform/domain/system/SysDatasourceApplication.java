package com.ak.platform.domain.system;

import lombok.Data;

import java.util.List;

/**
 * 子系统数据源中间表 sys_application_datasource
 *
 * @author Vean
 */
@Data
public class SysDatasourceApplication {

    /**
     * 数据源编码
     */
    private String datasourceCode;

    /**
     * 子系统编码
     */
    private String appCode;

    /**
     * ===== 以下属性做业务处理使用 =====
     **/
    // 数据源关联子系统时选择的子系统
    private List<String> appCodesNew;
    // 之前已经选择的子系统
    private String appCodesOld;
}
