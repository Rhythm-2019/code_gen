package com.mdnote.rym.config;

import com.mdnote.rym.exception.CodeEnum;
import com.mdnote.rym.exception.GeneratorException;
import com.mdnote.rym.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Rhythm-2019
 * @date: 2020/12/08
 * @description: 包保存用户设置信息
 * @vsersion: 1.0
 */
@Data
@NoArgsConstructor
@ToString
public class Settings implements BeanLifeCycle {

    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 备注信息
     */
    private String projectComment;
    /**
     * 期望生成的代码类型
     */
    private String expectProjectType;

    /**
     * 公司名称
     */
    private String company;
    /**
     * 作者
     */
    private String author;
    /**
     * 项目版本
     */
    private String version;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 时间
     */
    private String dateTime;
    /**
     * 输出目录
     */
    private String outputPath;
    /**
     * 引擎
     */
    private String engine;

    @Override
    public void init() {
        if (StringUtil.isEmpty(this.projectName)) {
            throw new GeneratorException(CodeEnum.PROJECTNAME_PEROPERTY_ERROR, null);
        }
        if (StringUtil.isEmpty(this.outputPath)) {
            throw new GeneratorException(CodeEnum.OUTPUTPATH_PROPERTY_ERROR, null);
        }
        if (StringUtil.isEmpty(this.expectProjectType)) {
            throw new GeneratorException(CodeEnum.EXPECT_CODE_TYPE_ERROR, null);
        }
        this.dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    @Override
    public void destroy() {
    }

    public Map<String, Object> fieldToMap() {
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = this.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                map.put(declaredField.getName(), declaredField.get(this));
            } catch (IllegalAccessException e) {
                throw new GeneratorException(CodeEnum.UNKNOWN, e);
            }
        }
        return map;
    }
}
