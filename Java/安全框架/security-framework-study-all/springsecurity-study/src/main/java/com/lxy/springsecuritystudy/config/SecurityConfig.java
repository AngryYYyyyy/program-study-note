package com.lxy.springsecuritystudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/1 15:51
 * @Description：
 */
@Configuration
public class SecurityConfig implements WebSecurityConfigurer {
    @Override
    public void init(SecurityBuilder builder) throws Exception {

    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {

    }
}
