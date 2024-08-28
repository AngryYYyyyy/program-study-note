package com.mashibing.servicepassengeruser.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "service-passenger-user";
    }

    @Value("${ceshi-application}")
    String ceshi;

    @GetMapping("/get-config")
    public String getConfig(){
        return ceshi;
    }
}
