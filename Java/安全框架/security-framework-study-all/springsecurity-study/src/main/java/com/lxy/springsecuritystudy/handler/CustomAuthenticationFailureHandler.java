package com.lxy.springsecuritystudy.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义认证失败处理器，用于处理认证过程中发生的失败。
 * 在用户认证失败时，该处理器被触发，并返回一个JSON格式的错误响应。
 * 这对于前后端分离的应用尤其有用，前端应用能够根据返回的JSON消息显示适当的错误信息。
 *
 * @author AngryYYYYYY
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 在用户认证失败时被调用，用于处理认证失败的情况。
     * 该方法设置响应的状态码为401（Unauthorized），并将错误信息转换为JSON格式输出。
     *
     * @param request 表示客户端请求的HttpServletRequest对象。
     * @param response 表示服务端响应的HttpServletResponse对象。
     * @param exception 表示认证过程中抛出的异常，包含错误详情。
     * @throws IOException 如果处理响应输出时发生IO异常。
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        // 输出错误信息，将异常消息封装到JSON格式中，提供给前端详细的错误描述
        response.getWriter().write(objectMapper.writeValueAsString("Error: " + exception.getMessage()));
    }
}

