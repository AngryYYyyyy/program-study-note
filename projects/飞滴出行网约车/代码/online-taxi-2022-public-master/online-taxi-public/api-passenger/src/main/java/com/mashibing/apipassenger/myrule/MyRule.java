package com.mashibing.apipassenger.myrule;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

//@Component
public class MyRule {

    @Bean
    public ReactorServiceInstanceLoadBalancer myLoadBalancerRule(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers){
        return new MyLoadBalancerRule(serviceInstanceListSuppliers);
    }
}
