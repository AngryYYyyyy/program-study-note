package com.lxy.springsecuritystudy.config;


import com.lxy.springsecuritystudy.filter.JwtAuthenticationTokenFilter;
import com.lxy.springsecuritystudy.handler.CustomAccessDeniedHandler;
import com.lxy.springsecuritystudy.handler.CustomAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;


/**
 * Spring Security配置类，用于配置安全性和身份验证参数。
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/1 15:51
 * @Description：定义了安全配置，包括密码编码器、会话管理、授权请求配置以及添加自定义JWT认证过滤器。
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;  // JWT身份验证过滤器，用于拦截请求并进行身份验证
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;//自定义权限异常响应处理
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;//自定义认证异常响应处理

    /**
     * 配置密码编码器。
     * 使用 BCryptPasswordEncoder 来安全地加密密码。
     * @return 返回 BCryptPasswordEncoder 实例，用于密码加密。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义并配置 SecurityFilterChain，用于处理HTTP安全性。
     * @param http HttpSecurity配置对象，用于定义请求的安全处理。
     * @return SecurityFilterChain 实例，表示配置好的HTTP安全链。
     * @throws Exception 抛出异常，如果配置过程中出现错误。
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 应用CORS配置
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 禁用 CSRF 防护，适用于无状态的API服务,在前后端分离的架构中，通常禁用
                .csrf(AbstractHttpConfigurer::disable)

                // 配置会话管理策略，STATELESS表示不使用HTTPSession进行会话管理
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 在用户名密码认证过滤器之前添加自定义JWT过滤器，用于验证JWT令牌
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

                // 配置授权请求，定义哪些URL路径应该被保护
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()  // 允许所有用户访问登录接口
                        .requestMatchers(HttpMethod.GET,"/captcha").permitAll()
                        .anyRequest().authenticated()  // 其他所有请求都需要认证
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .permitAll()
                )

                //配置异常处理器
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler));

        return http.build();
    }

    /**
     * 配置 AuthenticationManager Bean，用于处理实际的认证。
     * @param authenticationConfiguration 认证配置，由Spring Security提供
     * @return 返回配置好的 AuthenticationManager 实例。
     * @throws Exception 抛出异常，如果认证管理器配置失败。
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置 CorsConfigurationSource Bean，用于cors。
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // 允许所有源
        configuration.setAllowedMethods(Collections.singletonList("*")); // 允许所有HTTP方法
        configuration.setAllowedHeaders(Collections.singletonList("*")); // 允许所有头
        configuration.setAllowCredentials(true); // 允许凭证

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 应用于所有路径

        return source;
    }
}
