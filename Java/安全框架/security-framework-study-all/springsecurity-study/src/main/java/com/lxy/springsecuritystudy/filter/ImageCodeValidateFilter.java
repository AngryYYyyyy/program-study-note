package com.lxy.springsecuritystudy.filter;

import com.lxy.springsecuritystudy.utils.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/20 14:08
 * @Description：
 */
@Component
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;  // 注入RedisCache

    private static final String CAPTCHA_CODE_KEY = "captchaCode:";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 只在特定的请求路径上应用这个过滤器，例如登录
        if ("/login".equals(request.getRequestURI()) && "POST".equalsIgnoreCase(request.getMethod())) {
            try {
                validateCaptcha(request);
            } catch (CaptchaException e) {
                // 如果验证失败，可以返回错误信息
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
                return;  // 验证码错误时，不再继续过滤链
            }
        }

        // 如果不是登录请求或验证码正确，继续其他过滤器链
        filterChain.doFilter(request, response);
    }

    private void validateCaptcha(HttpServletRequest request) throws CaptchaException {
        String uuid = request.getParameter("uuid");
        String userInputCaptcha = request.getParameter("captcha");
        if (userInputCaptcha == null || uuid == null) {
            throw new CaptchaException("uuid或者captcha为空");
        }
        userInputCaptcha = userInputCaptcha.toLowerCase();  // 转换小写
        String key = CAPTCHA_CODE_KEY + uuid;
        String correctCaptcha = redisCache.getCacheObject(key);

        if (correctCaptcha == null || !correctCaptcha.equals(userInputCaptcha)) {
            throw new CaptchaException("验证码错误或已过期");
        }

        // 验证通过后，删除Redis中的验证码
        redisCache.deleteObject(key);
    }

    // 自定义异常
    private static class CaptchaException extends Exception {
        public CaptchaException(String message) {
            super(message);
        }
    }
}
