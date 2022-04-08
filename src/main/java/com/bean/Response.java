package com.bean;

import java.io.Serializable;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 1:55 PM
 * Modified By:
 */
public class Response<T> implements Serializable {
    private T data;
    private String message = "success";
    private int code;

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
