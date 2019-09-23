package com.ak.common.utils;

import com.ak.common.enums.RandomType;

/**
 * 编码规则生成器
 *
 * @author Vean
 */
public class CodeGenerateUtils {

    /**
     * 租户编码生成方法
     *
     * @return 租户编码
     */
    public static String tenantCodeGenerate() {
        return StringUtils.random(6, RandomType.STRING);
    }

    /**
     * 数据源编码生成方法
     *
     * @return 数据源编码
     */
    public static String datasourceCodeGenerate() {
        return StringUtils.random(6, RandomType.ALL);
    }
}
