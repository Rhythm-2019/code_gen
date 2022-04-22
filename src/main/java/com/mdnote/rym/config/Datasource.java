package com.mdnote.rym.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

/**
 * author:Rhythm-2019
 * date: 2020/3/9
 * description: 存储数据库信息的实体类
 */
@Data
@NoArgsConstructor
@ToString
public class Datasource implements BeanLifeCycle {

    /**
     * IP 地址
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 数据库，欧盟和从
     */
    private String databaseName;
    /**
     * 数据量类型
     */
    private String datasourceType;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 数据库类型枚举
     */
    private DatabaseTypeEnum dsType;

    /**
     *  JDBC地址
     */
    private String jdbcUrl;

    /**
     * 驱动名称
     */
    private String driverClassName;

    @Override
    public void init() {
        this.dsType = DatabaseTypeEnum.of(this.datasourceType);
        if (this.dsType == null) {
            throw new IllegalArgumentException("不支持的数据库类型");
        }
        this.driverClassName = this.dsType.driver;
        this.jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8&serverTimezone=GMt",
                this.ip, this.port, this.databaseName);
    }

    @Override
    public void destroy() {
    }

    public enum DatabaseTypeEnum {
        /**
         * MySQL 数据量
         */
        MYSQL("mysql", "com.mysql.cj.jdbc.Driver");

        private String name;
        private String driver;

        DatabaseTypeEnum(String name, String driver) {
            this.name = name;
            this.driver = driver;
        }

        public static DatabaseTypeEnum of(String name) {
            for (DatabaseTypeEnum type : values()) {
                if (type.getName().equalsIgnoreCase(name)) {
                    return type;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }
}
