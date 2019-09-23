package com.ak.framework.datasource;

import lombok.extern.log4j.Log4j2;

/**
 * @author Vean
 */
@Log4j2
public final class DataSourceHolder {

    private static final ThreadLocal<DataSourceBeanBuilder> THREAD_LOCAL = new ThreadLocal<DataSourceBeanBuilder>();

    static DataSourceBeanBuilder getDataSource() {
        return THREAD_LOCAL.get();
    }

    /**
     * 使用该方法设置数据源
     **/
    public static void setDataSource(DataSourceBeanBuilder dataSourceBeanBuilder) {
        log.debug("切换到{}数据源", dataSourceBeanBuilder.getBeanName());
        THREAD_LOCAL.set(dataSourceBeanBuilder);
    }

    /**
     * 使用该方法清除数据源，清除后将使用默认数据源
     **/
    public static void clearDataSource() {
        THREAD_LOCAL.remove();
    }
}
