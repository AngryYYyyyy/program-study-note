package com.msb.hjycommunity.common.core.exception;

/**
 * 用户密码不正确异常类
 * @author spikeCong
 * @date 2023/5/8
 **/
public class UserPasswordNotMatchException extends CustomException {

    public UserPasswordNotMatchException() {
        super(400,"用户不存在/密码错误");
    }
}
