package com.ak.framework.datasource;

import com.ak.common.exception.BusinessException;
import com.ak.platform.domain.system.SysDatasource;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 数据源实体构建类
 *
 * @author Vean
 */
@Log4j2
public class DataSourceBeanBuilder {
    private static final String URL_FORMATTER = "%s%s:%s%s%s";
    /**
     * Bean统一交给Spring管理，所以beanName必须唯一
     */
    @Getter
    private final String beanName;
    @Getter
    private final String databaseIp;
    @Getter
    private final String databasePort;
    @Getter
    private final String driverClassName;
    @Getter
    private final String databaseName;
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    private String validationQuery = "select 1 from dual";
    private boolean testOnBorrow = true;

    public DataSourceBeanBuilder(SysDatasource dataSource) {
        /**唯一、主键**/
        this.beanName = dataSource.getDatasourceCode();
        this.databaseIp = dataSource.getDatabaseIp();
        this.databasePort = dataSource.getDatabasePort();
        this.driverClassName = dataSource.getDatabaseDriverClassName();
        this.databaseName = dataSource.getDatabaseName();
        this.username = dataSource.getDatabaseUsername();
        this.password = dataSource.getDatabasePassword();
    }

    public DataSourceBeanBuilder validationQuery(String value) {
        this.validationQuery = value;
        return this;
    }

    public DataSourceBeanBuilder testOnBorrow(boolean value) {
        this.testOnBorrow = value;
        return this;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public String getUrl() {
        if (driverClassName.contains("mysql")) {
            String url = String.format(URL_FORMATTER, "jdbc:mysql://", this.databaseIp, this.databasePort, "/",
                    this.databaseName)
                    + "?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&autoReconnect=true";
            log.info(url);
            return url;
        } else {
            throw new BusinessException("暂时只支持MySQL数据库链接");
        }
    }

    @Override
    public String toString() {
        return "DataSourceBeanBuilder{" + "driverClassName='" + driverClassName + '\'' + ", databaseIp='" + databaseIp
                + '\'' + ", databasePort='" + databasePort + '\'' + ", databaseName='" + databaseName + '\''
                + ", username='" + username + '\'' + ", password='" + password + '\'' + ", validationQuery='"
                + validationQuery + '\'' + ", testOnBorrow=" + testOnBorrow + '}';
    }
}