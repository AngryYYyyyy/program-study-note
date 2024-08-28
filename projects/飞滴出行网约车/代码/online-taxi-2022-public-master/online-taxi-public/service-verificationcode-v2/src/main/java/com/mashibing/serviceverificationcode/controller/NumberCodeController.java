package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class NumberCodeController {

//    @Value("${spring.cloud.nacos.discovery.cluster-name}")
//    String clusterName;

    @Value("${server.port}")
    String port;

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size ){

        System.out.println("验证码:"+port);
        // 生成6位字母 验证码

        String sourceChar = "abcdefghigklmnopqrstuvwxyz";
        char[] m = sourceChar.toCharArray();
        String resultString = "";
        for (int i=0;i<size;i++){
            char temp = m[new Random().nextInt(26)];

            resultString = resultString + temp;

        }

        // 定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultString);


        return ResponseResult.success(response);
    }
}
