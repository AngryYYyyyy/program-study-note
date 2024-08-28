package com.mashibing.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CustomCircuirtBreakerConfig {

    @Bean
    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(){

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.TIME_BASED)// 设置窗口类型为时间窗口
                .slidingWindowSize(2) // 窗口大小2s
                .minimumNumberOfCalls(5) // 最少的请求数 5次
                .failureRateThreshold(80) // 失败阈值 40%
                .enableAutomaticTransitionFromOpenToHalfOpen() // 运行开关自动从打开状态切换到半开状态
                .waitDurationInOpenState(Duration.ofSeconds(1)) // 断路器从打开状态到半开状态的时长是10s
                .permittedNumberOfCallsInHalfOpenState(5) // 在半开状态下，允许进行正常调用的次数
                .recordExceptions(Throwable.class)
                .build();

        ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
        factory.configureDefault(id->new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(600)).build())
                .circuitBreakerConfig(circuitBreakerConfig).build()
        );

        return factory;


    }
}
