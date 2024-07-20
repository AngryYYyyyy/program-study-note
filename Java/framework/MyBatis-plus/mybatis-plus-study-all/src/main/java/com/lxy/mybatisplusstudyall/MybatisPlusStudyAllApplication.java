package com.lxy.mybatisplusstudyall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lxy.mybatisplusstudyall.mapper")
public class MybatisPlusStudyAllApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusStudyAllApplication.class, args);
    }

}
