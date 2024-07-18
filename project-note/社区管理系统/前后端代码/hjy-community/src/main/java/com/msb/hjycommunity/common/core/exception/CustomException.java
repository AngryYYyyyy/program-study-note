package com.msb.hjycommunity.common.core.exception;

import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;

/**
 * 业务异常基类
 * @author spikeCong
 * @date 2023/5/8
 **/
public class CustomException extends RuntimeException {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 数据部分
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    public CustomException() {

    }

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = HttpServletResponse.SC_OK == code;
    }

    public CustomException(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public CustomException(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
