package com.mashibing.apiBoss.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RefreshScope
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "api-boss";
    }

    static int index = 0;

    @GetMapping("/testCircuitBreaker")
    public String testCircuitBreaker(){
        System.out.println("index="+index);
        if (index == 0){
            index = 1;
        }else if (index == 1){
            index = 0;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return "api-boss="+index;
    }


    @Value("${testConfig}")
    private String testConfig;


    @GetMapping("/getTestConfig")
    public String getConfig(){
        System.out.println("t:"+testConfig);
        return  testConfig;
    }
}
