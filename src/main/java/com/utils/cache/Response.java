package com.utils.cache;

import com.enums.ResponseCode;
import com.exception.ErrorCode;
import com.exception.GeneralException;

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

    public Response(int code, T data) {
        this.code = code;
        this.data = data;
    }


    public Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
    }

    public Response(ResponseCode responseCode, T data) {
        this.code = responseCode.getCode();
        this.message = responseCode.getMsg();
        this.data = data;
    }

    public static Response error(GeneralException e) {
        Response response = new Response();
        long errorCode = e.getCode().getCode();
        response.setCode((int) errorCode);
        response.setMessage(e.getCode().getMessage());
        return response;
    }

    public static Response error(ErrorCode e) {
        Response response = new Response();
        response.setMessage(e.getMessage());
        response.setCode((int) e.getCode());
        return response;
    }

    public Response(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public Response(int code) {
        this.code = code;
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
