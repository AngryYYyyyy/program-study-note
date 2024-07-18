package com.msb.hjycommunity.common.core.exception;

import com.msb.hjycommunity.common.core.exception.BaseException;
import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码异常
 * @author spikeCong
 * @date 2023/5/1
 **/
public class CaptchaNotMatchException extends CustomException {

    public CaptchaNotMatchException() {
        super(400,"验证码错误");
    }
}
