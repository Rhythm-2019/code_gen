package com.mdnote.rym.collector;

import com.mdnote.rym.config.Datasource;
import com.mdnote.rym.consts.GenerateConst;
import com.mdnote.rym.model.Metadata;
import com.mdnote.rym.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Properties;

/**
 * @author Rhythm-2019
 * @date 2022/4/20
 * @description 元数据采集器
 */
public interface MetadataCollector {

    static MySQLMetadataCollector newInstance(Datasource datasource) {

        // 加载类型和数据库映射关系
        Properties properties = PropertiesUtil.readAllProperties(String.format(GenerateConst.TYPE_MAP_FORMAT_STR, GenerateConst.LOCAL_DIR, datasource.getDsType().getName()));
        HashMap<String, String> typeMap = new HashMap<>();
        properties.forEach((k, v) -> {
            typeMap.put(String.valueOf(k), String.valueOf(v));
        });
        switch (datasource.getDsType()) {
            case MYSQL: {
                return new MySQLMetadataCollector(datasource, typeMap);
            }
            // TODO 拓展支持其他数据库
            default: {
                throw new IllegalArgumentException("目前只支持 MySQL 数据库");
            }
        }
    }

    /**
     * 获取元数据
     *
     * @return 元数据封装，包含表结构信息
     */
    Metadata getMetadata();
}
