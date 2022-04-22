package com.mdnote.rym.consts;

/**
 * @author Rhythm-2019
 * @version 1.0
 * @Date 2020/12/13 11:53
 * @Email rhythm_2019@163.com
 * @Description 代码生成常量
 */
public class GenerateConst {
    /**
     * 当前类加载器的根目录
     */
    public static final String LOCAL_DIR = GenerateConst.class.getClassLoader().getResource("").getPath();
    ;
    /**
     * 配置文件名称
     */
    public static final String CONFIG_FILE_NAME = LOCAL_DIR + "/generator.properties";
    /**
     * 类型转换文件路径格式
     */
    public static final String TYPE_MAP_FORMAT_STR = "%stypemap/%s.properties";
}
