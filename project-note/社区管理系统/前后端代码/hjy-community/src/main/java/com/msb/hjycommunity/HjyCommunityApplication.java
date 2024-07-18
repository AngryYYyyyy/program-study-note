package com.msb.hjycommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.msb.hjycommunity.**.mapper")
public class HjyCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(HjyCommunityApplication.class, args);

        System.out.println("合家云后台系统启动成功！！！！！");
    }

}
