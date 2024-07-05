package com.example.jvmcase.controller;

import com.example.jvmcase.utils.MetaspaceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NonHeapController {

    List<Class<?>> list = new ArrayList<Class<?>>();

    @GetMapping("/nonheap")
    public String heap() {
        while (true) {
            list.addAll(MetaspaceUtil.createClasses());
        }
    }
}
