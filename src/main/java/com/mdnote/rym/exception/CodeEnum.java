package com.mdnote.rym.exception;

/**
 * @author Rhythm-2019
 * @version 1.0
 * @Date 2020/12/9 16:50
 * @Email rhythm_2019@163.com
 * @Description 异常状态码
 */
public enum CodeEnum {
    DRIVER_NO_FOUND(501, "找不到驱动，请检查是否添加依赖"),
    CONNECT_DB_FAILED(502, "数据库连接失败，请检查网络或账号密码"),
    IP_PROPERTY_ERROR(503, "数据库IP配置有误"),
    PORT_PROPERTY_ERROR(504, "数据库端口配置有误"),
    USERNAME_PROPERTY_ERROR(505, "数据库用户名配置有误"),
    PASSWORD_PROPERTY_ERROR(506, "数据库密码配置错误"),
    DATABASENAME_PROPERTY_ERROR(507, "数据库名配置错误"),
    DBTYPE_PROPERTY_ERROR(508, "数据库类型置错误"),
    DBTYPE_ONLY_SUPPORT_MYSQL(510, "当前版本只支持MySQL数据库"),

    PROJECTNAME_PEROPERTY_ERROR(520, "项目名称名配置有误"),
    BASEPACKAGE_PROPERTY_ERROR(521, "项目基础包名配置有误"),
    OUTPUTPATH_PROPERTY_ERROR(522, "输出目录配置有误"),
    EXPECT_CODE_TYPE_ERROR(524, "输出目录配置有误"),
    CREATE_FILE_FAILED(523, "无法创建文件夹/文件，请检查输出目录是否填写正确"),

    CLASS_NO_FOUND(10006, "未找到对应的类"),
    SQL_EXECUTE_FAILED(10007, "SQL执行失败"),
    READ_FILE_FAILED(10008, "读取文件失败"),
    GENERATRD_FAILED(10009, "代码生成失败，详细请看控制台"),
    UNKNOWN(10010, "遇见未知异常，详细请查看控制台"),
    BAD_ENGINE(10012, "引擎异常");

    private int code;
    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
