package com.lxy.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 14:44
 * @Description：
 */
@SpringBootApplication
@MapperScan("com.lxy.example.mapper")
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class,args);
    }
}