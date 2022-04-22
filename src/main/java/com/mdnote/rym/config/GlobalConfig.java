package com.mdnote.rym.config;

import com.mdnote.rym.consts.GenerateConst;
import com.mdnote.rym.exception.GeneratorException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author Rhythm-2019
 * @date 2021/7/9
 * @description 配置上下文
 */
@Getter
public class GlobalConfig implements BeanLifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalConfig.class);
    /**
     * 数据源相关配置
     */
    @ConfigPropertyPrefix("generator.database")
    private Datasource database;

    /**
     * 生成器相关配置
     */
    @ConfigPropertyPrefix("generator.settings")
    private Settings settings;


    public static GlobalConfig getInstance() {
        GlobalConfig cfg = new GlobalConfig();
        cfg.init();
        return cfg;
    }

    @Override
    public void init() {
        // 通过配置文件初始化带 @ConfigPropertyPrefix 的成员变量
        try (FileReader configFile = new FileReader(new File(GenerateConst.CONFIG_FILE_NAME));) {
            Properties properties = new Properties();
            properties.load(configFile);
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                ConfigPropertyPrefix configPropertyPrefixAnnotation = field.getAnnotation(ConfigPropertyPrefix.class);
                if (configPropertyPrefixAnnotation == null) {
                    continue;
                }

                String propertyPrefix = configPropertyPrefixAnnotation.value();
                Class<?> clz = Class.forName(field.getType().getName());
                Object obj = clz.newInstance();
                for (Field inlineField : clz.getDeclaredFields()) {
                    inlineField.setAccessible(true);
                    Object value = properties.get(propertyPrefix + "." + inlineField.getName());
                    if (value != null) {
                        inlineField.set(obj, value);
                    }
                }
                field.set(this, obj);

                if (obj instanceof BeanLifeCycle) {
                    BeanLifeCycle bean = (BeanLifeCycle) obj;
                    // 执行成员属性的初始化方法
                    bean.init();
                }
            }

        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw GeneratorException.unexpect(e);
        }
    }

    @Override
    public void destroy() {
        try {
            for (Field field : this.getClass().getFields()) {
                Object obj = field.get(this);
                if (obj instanceof BeanLifeCycle) {
                    BeanLifeCycle bean = (BeanLifeCycle) obj;
                    bean.destroy();
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("对象销毁失败，堆栈信息: {}", (Object) e.getStackTrace());
        }
    }
}
