package com.lxy.springsecuritystudy.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * 自定义认证成功处理器，用于处理用户认证成功后的操作。
 * 当用户在前后端分离的应用中成功登录时，该处理器将被调用。
 * 它返回一个包含用户主要信息的JSON格式响应。
 *
 * @author AngryYYYYYY
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 在认证成功后被调用，用于处理成功后的逻辑。
     * 该方法设置响应的状态码为200（OK），并将认证对象的主要信息转换为JSON格式输出。
     *
     * @param request 表示客户端请求的HttpServletRequest对象。
     * @param response 表示服务端响应的HttpServletResponse对象。
     * @param authentication 表示认证成功后的认证信息对象。
     * @throws IOException 如果处理响应输出时发生IO异常。
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        // 将authentication对象中的主要信息转为JSON格式，并写入响应体
        response.getWriter().write(objectMapper.writeValueAsString(authentication.getPrincipal()));
    }
}

