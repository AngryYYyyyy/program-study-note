package com.lxy.springsecuritystudy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/22 22:26
 * @Description：
 */
@RestController
public class HelloController {
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
