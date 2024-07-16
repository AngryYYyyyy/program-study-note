package com.msb.hjycommunity.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * 通用的配置
 * @author spikeCong
 * @date 2023/5/8
 **/
@Configuration
public class ResourcesConfig {

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();

        // 设置访问源地址
        config.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 设置访问源请求头
        config.addAllowedHeader(CorsConfiguration.ALL);

        // 设置访问源请求方法
        config.addAllowedMethod(CorsConfiguration.ALL);

        //允许凭证
        config.setAllowCredentials(true);
        // 对接口配置跨域设置
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
