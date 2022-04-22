package com.mdnote.rym.model;

import lombok.ToString;

import java.util.Iterator;
import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/4/21
 * @description
 */
@ToString
public class Metadata {

    private int tableCount;
    private String databaseName;
    private List<Table> tableList;

    public Metadata(String databaseName, List<Table> tableList) {
        this.tableCount = tableList.size();
        this.databaseName = databaseName;
        this.tableList = tableList;
    }

    public Iterator<Table> getTableIterable() {
        return this.tableList.iterator();
    }
}
