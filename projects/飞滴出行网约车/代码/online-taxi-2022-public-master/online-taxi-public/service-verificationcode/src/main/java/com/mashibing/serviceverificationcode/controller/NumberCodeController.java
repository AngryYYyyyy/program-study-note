package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.responese.NumberCodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class NumberCodeController {

//    @Value("${spring.cloud.nacos.discovery.cluster-name}")
//    String clusterName;

    @Value("${server.port}")
    String port;

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size , HttpServletRequest request){

        String tag = request.getHeader("tag");
        System.out.println("验证码服务收到的tag："+tag);

        System.out.println("验证码:"+port);
        // 生成验证码
        double mathRandom = (Math.random()*9 + 1) * (Math.pow(10,size-1));
        System.out.println(mathRandom);
        int resultInt = (int)mathRandom;
        System.out.println("generator src code:"+resultInt);

//        System.out.println("数据中心，区域："+clusterName);

        // 定义返回值
        NumberCodeResponse response = new NumberCodeResponse();
        response.setNumberCode(resultInt+"");


        // 睡眠1min
//        try {
//            TimeUnit.MINUTES.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        return ResponseResult.success(response);
    }
}
