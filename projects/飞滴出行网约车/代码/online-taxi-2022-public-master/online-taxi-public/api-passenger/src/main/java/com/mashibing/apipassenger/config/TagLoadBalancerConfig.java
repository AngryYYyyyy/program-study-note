package com.mashibing.apipassenger.config;


import com.mashibing.apipassenger.myrule.TagLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class TagLoadBalancerConfig {

    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory){

        String property = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);

        return new TagLoadBalancer(property, loadBalancerClientFactory.getLazyProvider(property, ServiceInstanceListSupplier.class));
    }
}