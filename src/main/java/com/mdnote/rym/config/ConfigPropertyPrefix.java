package com.mdnote.rym.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Rhythm-2019
 * @date 2022/4/20
 * @description 加载配置文件配置
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPropertyPrefix {
    /**
     * @return 配置文件的前缀，后面京跟着字段名称
     */
    String value() default "";
}
