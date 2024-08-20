package com.lxy.springsecuritystudy.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义认证入口点，用于处理需要认证的请求但用户未认证的情况。
 * 当访问需要认证的资源而用户未登录时，此入口点将被调用。
 *
 * @author AngryYYYYYY
 * @date 2024/08/20
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 在用户未认证的情况下启动认证过程或返回相应的错误信息。
     * 当访问被保护的资源而用户未经认证时，该方法会被触发。
     *
     * @param request  表示客户端请求的 HttpServletRequest 对象
     * @param response 表示服务端响应的 HttpServletResponse 对象
     * @param authException 认证失败时抛出的异常
     * @throws IOException 可能抛出的 IO 异常，通常是由于响应写入失败
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Please login.");
    }
}

