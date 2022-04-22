package com.mdnote.rym.core;

import com.mdnote.rym.config.GlobalConfig;
import com.mdnote.rym.engine.IEngine;
import com.mdnote.rym.engine.CommonEngine;
import com.mdnote.rym.exception.GeneratorException;
import com.mdnote.rym.model.Metadata;
import com.mdnote.rym.model.Table;
import com.mdnote.rym.collector.MetadataCollector;
import com.mdnote.rym.util.BeanUtil;

import java.beans.BeanInfo;
import java.util.Iterator;

/**
 * @author Rhythm-2019
 * @date 2022/4/21
 * @description 生成器
 */
public class CoreGenerator {

    /**
     * 元数据
     */
    private Metadata metadata;
    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    /**
     * 单例
     */
    private static CoreGenerator INSTANCE;

    public static CoreGenerator newInstance() {
        if (INSTANCE == null) {
            synchronized (CoreGenerator.class) {
                if (INSTANCE == null) {
                    INSTANCE = innerNewInstance();
                }
            }
        }
        return INSTANCE;
    }

    private static CoreGenerator innerNewInstance() {
        // 加载数据源配置
        CoreGenerator gen = new CoreGenerator();
        gen.globalConfig = GlobalConfig.getInstance();
        ;
        // 加载元数据
        gen.metadata = MetadataCollector.newInstance(gen.globalConfig.getDatabase()).getMetadata();
        return gen;
    }

    public void execute() {

        // 创建引擎
        IEngine engine = null;
        try {
            engine = (IEngine) BeanUtil.newInstance(this.globalConfig.getSettings().getEngine());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw GeneratorException.unexpect(e);
        }

        // 进行准备工作，如读取引擎配置文件、初始化模板工具
        engine.prepare(this.globalConfig);

        // 生成非表结果相关内容
        engine.handle();
        // 表驱动代码生成
        Iterator<Table> iterator = this.metadata.getTableIterable();
        while (iterator.hasNext()) {
            Table table = iterator.next();
            engine.handle(table);
        }

        // 释放资源
        engine.release();
    }

}
