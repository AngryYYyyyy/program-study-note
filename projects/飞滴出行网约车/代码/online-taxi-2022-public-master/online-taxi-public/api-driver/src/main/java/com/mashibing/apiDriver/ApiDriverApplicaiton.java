package com.mashibing.apiDriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.mashibing.apiDriver","com.mashibing.internalcommon.remote"})
public class ApiDriverApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(ApiDriverApplicaiton.class);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest httpServletRequest){
        String day = httpServletRequest.getHeader("day1");
        System.out.println("请求头，网关加的:"+day);
        return "api-driver";
    }
}