package com.lxy.springsecuritystudy.filter;

import com.lxy.springsecuritystudy.handler.CustomAuthenticationFailureHandler;
import com.lxy.springsecuritystudy.model.dto.LoginUser;
import com.lxy.springsecuritystudy.utils.RedisCache;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.lxy.springsecuritystudy.utils.JwtUtils;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义 JWT 认证过滤器，用于验证请求中的 JWT 并从 Redis 加载用户详情，确保用户请求的合法性。
 * @author spikeCong
 * @date 2023/4/25
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache; // Redis 缓存服务，用于存取用户认证信息
    @Autowired
    private CustomAuthenticationFailureHandler failureHandler;

    /**
     * 对每个请求执行一次 JWT 认证逻辑。
     * @param request 请求对象
     * @param response 响应对象
     * @param filterChain 过滤器链
     * @throws ServletException, IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从请求头获取 JWT
        String token = request.getHeader("token");

        // 如果 token 为空，则继续处理请求链中的下一个过滤器
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // 解析 JWT 获取用户 ID
            Claims claims = JwtUtils.parseToken(token);
            String userId = claims.getSubject();

            // 从 Redis 获取用户信息
            String redisKey = "login:" + userId;
            LoginUser loginUser = redisCache.getCacheObject(redisKey);
            if (Objects.isNull(loginUser)) {
                throw new AuthenticationException("用户登录过期"){};
            }

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (AuthenticationException e) {
            // 使用失败处理器来处理异常
            failureHandler.onAuthenticationFailure(request, response, e);
        }finally {
            filterChain.doFilter(request, response);
    }
    }
}
