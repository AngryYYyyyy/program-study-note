package com.lxy.springsecuritystudy.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义访问拒绝处理器，用于处理授权失败的请求。
 * 当用户尝试访问无权访问的资源时，此处理器将被调用。
 *
 * @author AngryYYYYYY
 * @date 2024/08/20
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 处理访问被拒绝的情况，返回适当的错误信息。
     * 当用户已经认证但没有足够权限访问特定资源时，该方法会被触发。
     *
     * @param request  表示客户端请求的 HttpServletRequest 对象
     * @param response 表示服务端响应的 HttpServletResponse 对象
     * @param accessDeniedException 授权失败时抛出的异常
     * @throws IOException 可能抛出的 IO 异常，通常是由于响应写入失败
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied - You are not allowed to access this resource.");
    }
}
