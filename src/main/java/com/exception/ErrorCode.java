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
    DB_UPDATE_ERROR(111001004L, "update database error {0}", ErrorCodeType.GENERAL),
    DB_QUERY_ERROR(111001005L, "query database error {0}", ErrorCodeType.GENERAL),
    DB_DELETE_ERROR(111001006L, "delete database error {0}", ErrorCodeType.GENERAL),
    DB_INSERT_ERROR(111001007L, "insert database error {0}", ErrorCodeType.GENERAL),
    DB_INSERT_ERROR_EXISTED(111001008L, "insert database error, info existed {0}", ErrorCodeType.GENERAL),
    DB_QUERY_NOT_EXISTED_ERROR(111001009L, "query info not existed error {0}", ErrorCodeType.GENERAL),
    DB_QUERY_EXISTED_ERROR(111001010L, "query info not existed error {0}", ErrorCodeType.GENERAL),
    PERMISSION_DENIED(111001011L, "permission denied {0}", ErrorCodeType.GENERAL),
    ILLEGAL_DATA(111001012L, "illegal data {0}", ErrorCodeType.GENERAL),
    RATE_LIMIT_ERROR(111001013L, "{0}",ErrorCodeType.GENERAL),

    // USER 111 002 001
    USER_NOT_FOUND(111002001L, "user not found. details: --> {0}", ErrorCodeType.USER),
    USER_PASSWORD_WRONG(111002002L, "wrong password. details: --> {0}", ErrorCodeType.USER),
    USER_TOKEN_VERIFY_ERROR(111002004L, "TOKEN VERIFY ERROR. details: --> {0}", ErrorCodeType.USER),
    USER_TOKEN_EXPIRED(111002004L, "TOKEN EXPIRED. details: --> {0}", ErrorCodeType.USER),
    USER_INFO_EXISTED(111002005L, "you may use other id or name. details: --> {0}", ErrorCodeType.USER),
    USER_INFO_ILLEGAL(111002006L, "you may set null input", ErrorCodeType.USER);


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
