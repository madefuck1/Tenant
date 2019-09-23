package com.ak.framework.datasource;

import lombok.Getter;

/**
 * 数据源实体类
 *
 * @author Vean
 */
final class DataSourceBean {
    @Getter
    private final String beanName;
    @Getter
    private final String driverClassName;
    @Getter
    private final String url;
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private final String validationQuery;
    private final boolean testOnBorrow;

    public DataSourceBean(DataSourceBeanBuilder beanBuilder) {
        this.beanName = beanBuilder.getBeanName();
        this.driverClassName = beanBuilder.getDriverClassName();
        this.url = beanBuilder.getUrl();
        this.username = beanBuilder.getUsername();
        this.password = beanBuilder.getPassword();
        this.testOnBorrow = beanBuilder.isTestOnBorrow();
        this.validationQuery = beanBuilder.getValidationQuery();
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    @Override
    public String toString() {
        return "DataSourceBean{" + "driverClassName='" + driverClassName + '\'' + ", url='" + url + '\''
                + ", username='" + username + '\'' + ", password='" + password + '\'' + ", validationQuery='"
                + validationQuery + '\'' + ", testOnBorrow=" + testOnBorrow + '}';
    }
}