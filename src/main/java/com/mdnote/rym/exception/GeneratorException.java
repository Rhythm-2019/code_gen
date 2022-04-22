package com.mdnote.rym.exception;

import com.mdnote.rym.consts.GenerateConst;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Rhythm-2019
 * @version 1.0
 * @Date 2020/12/9 16:49
 * @Email rhythm_2019@163.com
 * @Description 代码生成异常
 */
public class GeneratorException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private CodeEnum codeEnum;

    public GeneratorException() {
        super();
    }

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return Objects.requireNonNull(codeEnum).getMessage();
    }

    public GeneratorException(CodeEnum codeEnum, Exception e) {
        this(codeEnum.getMessage(), e);
        this.codeEnum = codeEnum;
    }

    public static GeneratorException generateFailed(Exception e) {
        return new GeneratorException(CodeEnum.GENERATRD_FAILED, e);
    }

    public static GeneratorException unexpect(Exception e) {
        return new GeneratorException(CodeEnum.UNKNOWN, e);
    }

    public static GeneratorException badDBState(Exception e) {
        return new GeneratorException(CodeEnum.SQL_EXECUTE_FAILED, e);
    }

    public static GeneratorException badEngine(Exception e) {
        return new GeneratorException(CodeEnum.BAD_ENGINE, e);
    }

    public static GeneratorException readFileFailed(Exception e) {
        return new GeneratorException(CodeEnum.READ_FILE_FAILED, e);
    }
}
