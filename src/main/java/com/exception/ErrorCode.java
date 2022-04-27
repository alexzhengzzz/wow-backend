package com.exception;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 2:07 PM
 * Modified By:
 */
enum ErrorCodeType {
    GENERAL,
    USER,
    CORPORATION
}

public enum ErrorCode {
    //GENERAL 111 001 xxx
    UNKNOWN_ERROR(111001001L, "unknown error problem {0}", ErrorCodeType.GENERAL),
    REQUIRED_PARAM_NOT_PRESENT(111001002L, "required param [{0}] not present", ErrorCodeType.GENERAL),
    REQUIRED_INFO_IS_EMPTY(111001003L, "required info [{0}] not present", ErrorCodeType.GENERAL),

    // USER 111 002 001
    USER_NOT_FOUND(111002001L, "user not found. details: --> {0}", ErrorCodeType.USER),
    USER_PASSWORD_WRONG(111002002L, "wrong password. details: --> {0}", ErrorCodeType.USER),
    INSERT_DB_ERROR(111002003L, "insert database error: --> {0}", ErrorCodeType.USER),
    USER_TOKEN_VERIFY_ERROR(111002004L, "TOKEN VERIFY ERROR. details: --> {0}", ErrorCodeType.USER),
    USER_TOKEN_EXPIRED(111002004L, "TOKEN EXPIRED. details: --> {0}", ErrorCodeType.USER),
    USER_INFO_EXISTED(111002005L, "you may use other id or name. details: --> {0}", ErrorCodeType.USER),
    USER_INFO_ILLEGAL(111002006L, "you may set null input", ErrorCodeType.USER),
    UPDATE_DB_ERROR(111002007L, "update error", ErrorCodeType.USER),
    DELETE_DB_ERROR(111002008L,"delete error" , ErrorCodeType.USER);

    // CORPORATION 111 002 002


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
