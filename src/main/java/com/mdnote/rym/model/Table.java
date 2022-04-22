package com.mdnote.rym.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @version 1.0
 * @author: Rhythm-2019
 * @date: 2020/12/08
 * @description: 数据库实体类
 */
@Data
@ToString
@AllArgsConstructor
public class Table {

    /**
     * 数据库表名称
     */
    private String srcName;
    /**
     * 去拓峰名称
     */
    private String name;
    /**
     * 备注星系
     */
    private String comment;
    /**
     * 主键
     */
    private String keys;
    /**
     * 字段名称
     */
    private List<Column> columns;
}
