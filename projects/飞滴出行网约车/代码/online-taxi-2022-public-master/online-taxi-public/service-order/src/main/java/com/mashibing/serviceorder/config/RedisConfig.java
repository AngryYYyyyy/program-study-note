package com.mashibing.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

    private String potocol = "redis://";

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean("redissonBootYml")
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress(potocol+redisHost+":"+redisPort).setDatabase(0);

        return Redisson.create(config);

    }

    @Bean("redissonYamlClient")
    public RedissonClient redissonYamlClient(){
//        Config config = null;
//        try {
//            config = Config.fromYAML(new ClassPathResource("/redisson-config/single-server.yaml").getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }

    @Bean("redissonMasterSlaveClient")
    public RedissonClient redissonMasterSlaveClient(){
        Config config = null;
//        try {
//            config = Config.fromYAML(new ClassPathResource("/redisson-config/master-slave-server.yaml").getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }

    @Bean("redissonSentinelClient")
    public RedissonClient redissonSentinelClient(){
//        Config config = null;
//        try {
//            config = Config.fromYAML(new ClassPathResource("/redisson-config/sentinel.yaml").getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        return Redisson.create(config);

        // 便于测试
        return Redisson.create();

    }

    @Bean("redissonClusterClient")
    public RedissonClient redissonClusterClient(){
//        Config config = null;
//        try {
//            config = Config.fromYAML(new ClassPathResource("/redisson-config/cluster.yaml").getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return Redisson.create(config);

        // 便于测试
        return Redisson.create();



    }

    // 红锁
    @Bean("redissonClient1")
    public RedissonClient redissonClient1(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }
    @Bean("redissonClient2")
    public RedissonClient redissonClient2(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6380").setDatabase(0);
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }

    @Bean("redissonClient3")
    public RedissonClient redissonClient3(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6381").setDatabase(0);
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }

    @Bean("redissonClient4")
    public RedissonClient redissonClient4(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6382").setDatabase(0);
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }
    @Bean("redissonClient5")
    public RedissonClient redissonClient5(){
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6383").setDatabase(0);
//
//        return Redisson.create(config);
        // 便于测试
        return Redisson.create();
    }


    // 红锁


}
