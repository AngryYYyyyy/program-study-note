package com.msb.hjycommunity.common.core.domain;

import java.io.Serializable;

/**
 * 响应结果封装对象
 * @author spikeCong
 * @date 2023/3/1
 **/
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1901152752394073986L;

    /**
     * 响应状态码
     */
//    private String code;
    private int code;

    /**
     * 响应结果描述
     */
    private String msg;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 成功返回
     * @param data
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> success(T data){

        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(ResultCode.SUCCESS.getCode()));
        response.setMsg(ResultCode.SUCCESS.getMessage());
        response.setSuccess(true);
        response.setData(data);

        return response;
    }


    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String message){

        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(ResultCode.ERROR.getCode()));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String code, String message){

        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(message);

        return response;
    }

    /**
     * 失败返回 三个参数
     * @param message
     * @return: com.msb.hjycommunity.common.core.domain.BaseResponse<T>
     */
    public static <T> BaseResponse<T> fail(String code, String message,boolean success){

        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(Integer.parseInt(code));
        response.setMsg(message);
        response.setSuccess(success);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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
}
