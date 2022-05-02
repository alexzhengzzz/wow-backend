package com.exception;

import java.text.MessageFormat;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 2:19 PM
 * Modified By:
 */
public class GeneralExceptionFactory {
    public static GeneralException create(ErrorCode code, Object... args) {
        return create(code, null, args);
    }
    private GeneralExceptionFactory() {
    }
    public static GeneralException create(ErrorCode code, Throwable t, Object... args) {
        GeneralException exception = new GeneralException(code, t);
        String message = code.message;
        if (args != null && args.length > 0) {
            message = MessageFormat.format(message, args);
        }
        exception.setMessage(message);
        return exception;
    }
}
