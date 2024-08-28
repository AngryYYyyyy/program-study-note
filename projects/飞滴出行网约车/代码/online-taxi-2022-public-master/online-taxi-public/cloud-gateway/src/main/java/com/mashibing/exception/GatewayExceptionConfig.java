package com.mashibing.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class GatewayExceptionConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public MyExceptionHandler myExceptionHandler(){
        return new MyExceptionHandler();
    }
}
