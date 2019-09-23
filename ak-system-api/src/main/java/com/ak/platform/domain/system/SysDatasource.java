package com.ak.platform.domain.system;

import com.ak.common.core.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源表 sys_datasource
 *
 * @author Vean
 */
@Getter
@Setter
public class SysDatasource extends BaseEntity {

    private static final long serialVersionUID = -4339894349439046011L;

    /**
     * 数据源编码
     */
    private String datasourceCode;

    /**
     * 数据库IP
     */
    private String databaseIp;

    /**
     * 数据库端口
     */
    private String databasePort;

    /**
     * 数据库驱动类
     */
    private String databaseDriverClassName;

    /**
     * 数据库实例名
     */
    private String databaseName;

    /**
     * 数据库用户名
     */
    private String databaseUsername;

    /**
     * 数据库密码
     */
    private String databasePassword;

    /**
     * 数据库类型  留给今后做读写分离用，默认0（0：主库；1：从库）
     */
    private String databaseFlag;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 部门状态:0正常,1停用
     */
    private String status;

    private SysTenant tenant;

}
