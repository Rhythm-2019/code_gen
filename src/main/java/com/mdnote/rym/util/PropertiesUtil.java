package com.mdnote.rym.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mdnote.rym.exception.GeneratorException;
import sun.security.krb5.internal.PAEncTSEnc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Rhythm-2019
 * @version 1.0
 * @date 2020/12/9
 * @description: Properties工具类
 */
public class PropertiesUtil {

    /**
     * 项目中的所有配置
     */
    public static Map<String, String> constMap = new HashMap<>();

    static {
        // 加载配置文件
        File dir = new File("src/main/resources/");
        List<File> fileList = FileUtil.getFileList(dir);
        try {
            for (File file : fileList) {
                if (file.getName().endsWith(".properties")) {
                    Properties properties = new Properties();
                    properties.load(new FileReader(file));

                    Map<String, String> map = PropertiesUtil.putPropertiesInMap(properties);
                    constMap.putAll(map);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("遇到严重异常");
        }
    }

    /**
     * 将properties中全部放入Map中
     *
     * @param properties 配置对象
     * @return map
     */
    public static Map<String, String> putPropertiesInMap(Properties properties) {
        HashMap<String, String> map = new HashMap<>();
        Enumeration<Object> keys = properties.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = properties.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 获取常量集合
     *
     * @return 常量集合
     */
    public static Map<String, String> getConstMap() {
        return constMap;
    }

    public static Properties readAllProperties(String path) {
        try (FileReader fileReader = new FileReader(new File(path));) {
            Properties properties = new Properties();
            properties.load(fileReader);
            return properties;
        } catch (IOException e) {
            throw GeneratorException.readFileFailed(e);
        }
    }

    public static <T> Properties readAllJson(String path, Class<T> clz) {
        try (FileReader fileReader = new FileReader(new File(path));) {
            FileInputStream in = new FileInputStream(new File(path));
            return JSON.parseObject(in, clz);
        } catch (IOException e) {
            throw GeneratorException.readFileFailed(e);
        }
    }
}
