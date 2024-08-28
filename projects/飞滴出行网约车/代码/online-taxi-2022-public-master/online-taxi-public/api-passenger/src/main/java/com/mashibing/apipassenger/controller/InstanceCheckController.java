package com.mashibing.apipassenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test/instance-check")
public class InstanceCheckController {

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/check")
    public String check(){
        List<String> services = discoveryClient.getServices();
        for (String serviceName : services
             ) {
            // 获取到了服务名

            // 根据服务名获取服务实例
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
            for (ServiceInstance instance : instances
                 ) {
                System.out.println(instance.getServiceId());
                System.out.println(instance.getMetadata().get("name"));
                System.out.println(instance.getHost());
                System.out.println(instance.getPort());


            }

        }

        return "success";
    }
}
