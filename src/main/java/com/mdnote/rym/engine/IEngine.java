package com.mdnote.rym.engine;

import com.mdnote.rym.config.GlobalConfig;
import com.mdnote.rym.exception.GeneratorException;
import com.mdnote.rym.model.Table;

/**
 * @author Rhythm-2019
 * @date 2022/4/21
 * @description
 */
public interface IEngine {

    /**
     * 用于引擎初始化，加载配置文件等
     * @param globalConfig 配置信息
     */
    void prepare(GlobalConfig globalConfig);

    /**
     * 处理非表相关的代码生成
     */
    void handle();

    /**
     * 处理表相关的代码生成
     *
     * @param table 表结果
     */
    void handle(Table table);

    /**
     * 释放资源
     */
    void release();
}
