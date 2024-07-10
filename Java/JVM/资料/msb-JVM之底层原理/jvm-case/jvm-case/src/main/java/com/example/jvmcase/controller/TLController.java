package com.example.jvmcase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TLController {
    @GetMapping(value = "/tl")
    public String tl(HttpServletRequest request) {
        ThreadLocal<Byte[]> tl = new ThreadLocal<Byte[]>();
        tl.set(new Byte[1024 * 1024]);
        return "ok";
    }
}
