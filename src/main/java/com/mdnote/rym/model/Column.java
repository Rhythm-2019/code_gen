package com.mdnote.rym.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author:Rhythm-2019
 * @date: 2020/12/07
 * @version: 1.0
 * @description: 字段实体类
 */
@Data
@ToString
@AllArgsConstructor
public class Column {

    /**
     * 字段名
     */
    private String columnSrcName;
    /**
     * 去掉下划线的字段名
     */
    private String columnName;
    /**
     * JAVA类型
     */
    private String columnType;
    /**
     * 数据库类型
     */
    private String columnDbType;
    /**
     * 备注
     */
    private String columnComment;
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    /**
     * Mybatis JdbcType
     */
    private String mbJdbcType;

}
