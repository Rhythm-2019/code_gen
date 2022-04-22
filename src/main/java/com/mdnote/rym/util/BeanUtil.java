package com.mdnote.rym.util;

/**
 * @author Rhythm-2019
 * @date 2022/4/22
 * @description
 */
public class BeanUtil {

    public static Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clz = Class.forName(className);
        return clz.newInstance();
    }
}
