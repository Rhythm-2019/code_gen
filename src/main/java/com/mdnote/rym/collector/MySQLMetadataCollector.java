package com.mdnote.rym.collector;

import com.mdnote.rym.config.Datasource;
import com.mdnote.rym.exception.CodeEnum;
import com.mdnote.rym.exception.GeneratorException;
import com.mdnote.rym.model.Column;
import com.mdnote.rym.model.Metadata;
import com.mdnote.rym.model.Table;
import com.mdnote.rym.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

/**
 * @author Rhythm-2019
 * @date 2022/4/20
 * @description Mysql 元数据采集器
 */
@Slf4j
public class MySQLMetadataCollector implements MetadataCollector {

    /**
     * 数据库连接
     */
    private Connection connection;
    /**
     * 数据库
     */
    private Datasource datasource;
    /**
     * Java 类型-数据要类型的映射关系
     */
    private Map<String, String> typeMap;

    public MySQLMetadataCollector(Datasource datasource, HashMap<String, String> typeMap) {
        Properties properties = new Properties();
        System.out.println(datasource.getJdbcUrl());
        properties.put("user", datasource.getUsername());
        properties.put("password", datasource.getPassword());
        // 为了读取数据信息，需要允许访问 InformationSchema 数据库
        properties.put("useInformationSchema", "true");

        this.datasource = datasource;
        this.typeMap = typeMap;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(datasource.getJdbcUrl(), properties);
        } catch (ClassNotFoundException e) {
            log.error("");
            throw new GeneratorException(CodeEnum.CLASS_NO_FOUND, e);
        } catch (SQLException e) {
            throw GeneratorException.badDBState(e);
        }
    }

    @Override
    public Metadata getMetadata() {
        try {
            // 船舰元数据
            DatabaseMetaData metaData = this.connection.getMetaData();
            // 对实体类进行填充
            List<Table> tableList = new ArrayList<>();
            // 必须要%，非则拿不到表注释
            ResultSet tableResultSet = metaData.getTables(this.datasource.getDatabaseName(), "%", "%", new String[]{"TABLE"});
            while (tableResultSet.next()) {

                // 表名
                String srcName = tableResultSet.getString("TABLE_NAME");
                // 获取驼峰名称
                String name = StringUtil.getCamelSign(srcName, true);
                // 备注
                String comment = tableResultSet.getString("REMARKS");

                // 主键
                StringBuilder primaryKeysStringBuilder = new StringBuilder();
                ResultSet primaryKeysResultSet = metaData.getPrimaryKeys(null, null, srcName);
                while (primaryKeysResultSet.next()) {
                    primaryKeysStringBuilder.append(primaryKeysResultSet.getString("COLUMN_NAME")).append(",");
                }

                // 字段
                List<Column> columnList = new ArrayList<>();
                ResultSet columnsResultSet = metaData.getColumns(this.datasource.getDatabaseName(), "%", srcName, "%");
                while (columnsResultSet.next()) {
                    // 表原名称
                    String columnSrcName = columnsResultSet.getString("COLUMN_NAME");
                    // 驼峰名称
                    // TODO 最好区分一下首字母大小写的情况
                    String columnName = StringUtil.getCamelSign(columnSrcName, false);
                    // 数据库中字段的类型
                    String columnDbType = columnsResultSet.getString("TYPE_NAME");
                    // Java中类型
                    String columnJavaType = typeMap.get(columnDbType).split(",")[0];
                    // 注释
                    String columnComment = columnsResultSet.getString("REMARKS");
                    // 判断是否为主键
                    boolean isPrimaryKey = isPrimaryKey(primaryKeysStringBuilder.toString(), columnSrcName);
                    // Mybatis jdbcType
                    String jdbcTypeString = typeMap.get(columnDbType).split(",")[1];
                    String jdbcType = jdbcTypeString.substring("jdbcType=".length());

                    Column column = new Column(columnSrcName, columnName, columnJavaType, columnDbType, columnComment, isPrimaryKey, jdbcType);
                    columnList.add(column);
                }
                Table table = new Table(srcName, name, comment, primaryKeysStringBuilder.toString(), columnList);
                tableList.add(table);
            }

            return new Metadata(this.datasource.getDatabaseName(), tableList);
        } catch (SQLException e) {
            throw GeneratorException.badDBState(e);
        } finally {
            this.release();
        }
    }

    public void release() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                log.error("关闭连接失败", e);
            }
        }
    }

    private boolean isPrimaryKey(String primaryKeys, String columnSrcName) {
        primaryKeys = primaryKeys.toUpperCase();
        columnSrcName = columnSrcName.toUpperCase();
        String[] primaryKeyArray = primaryKeys.split(",");
        for (String s : primaryKeyArray) {
            if (columnSrcName.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
