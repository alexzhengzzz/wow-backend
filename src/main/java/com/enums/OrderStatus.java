package com.enums;

/**
 * @author alexzhengzzz
 * @date 5/4/22 01:03
 */
public enum OrderStatus {
    NEW(0),
    FINISHED(1);

    private int code;
    OrderStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
