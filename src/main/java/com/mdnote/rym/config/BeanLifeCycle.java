package com.mdnote.rym.config;

/**
 * @author Rhythm-2019
 * @date 2022/4/20
 * @description Bean 生命周期，用于管理创建、销毁
 */
public interface BeanLifeCycle {
    /**
     * 初始化
     */
    void init();

    /**
     * 销毁
     */
    void destroy();
}
