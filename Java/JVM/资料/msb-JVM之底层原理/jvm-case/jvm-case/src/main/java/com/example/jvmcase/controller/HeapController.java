package com.example.jvmcase.controller;

import com.example.jvmcase.domain.Worker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeapController {
    List<Worker> list = new ArrayList<Worker>();

    @GetMapping("/heap")
    public String heap() throws Exception {

        while (true) {
            list.add(new Worker());
//            Thread.sleep(1);

        }
    }
}
