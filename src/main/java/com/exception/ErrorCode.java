package com.exception;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 2:07 PM
 * Modified By:
 */
enum ErrorCodeType {
    GENERAL,
    USER
}

public enum ErrorCode {
    //GENERAL 111 001 xxx
    UNKNOWN_ERROR(111001001L, "unknown error", ErrorCodeType.GENERAL),
    REQUIRED_PARAM_NOT_PRESENT(111001002L, "required param [{0}] not present", ErrorCodeType.GENERAL),
    REQUIRED_INFO_IS_EMPTY(111001003L, "required info [{0}] not present", ErrorCodeType.GENERAL),

    //USER 111 002 001
    USER_NOT_FOUND(111002001L, "user not found. details: --> id : {0}", ErrorCodeType.USER),
    USER_PASSWORD_WRONG(111002002L, "wrong password. details: --> id : {0}", ErrorCodeType.USER),
    INSERT_USER_ERROR(111002003L, "add user error. details: --> id : {0}, name: {1}", ErrorCodeType.USER),
    USER_TOKEN_VERIFY_ERROR(111002004L, "TOKEN VERIFY ERROR. details: --> token : {0}", ErrorCodeType.USER),
    USER_TOKEN_EXPIRED(111002004L, "TOKEN EXPIRED. details: --> token: {0}", ErrorCodeType.USER);

    long code;
    String message;
    ErrorCodeType errorCodeType;

    ErrorCode(long code, String message, ErrorCodeType errorCodeType) {
        this.code = code;
        this.message = message;
        this.errorCodeType = errorCodeType;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ErrorCodeType getErrorCodeType() {
        return errorCodeType;
    }
}
