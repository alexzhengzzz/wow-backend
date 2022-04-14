package com.exception;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 2:14 PM
 * Modified By:
 */
public class GeneralException extends RuntimeException{

    private String message;
    private ErrorCode code;

    public GeneralException(ErrorCode code) {
        this.code = code;
    }

    public GeneralException(ErrorCode code, Throwable t) {
        super(t);
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

}
