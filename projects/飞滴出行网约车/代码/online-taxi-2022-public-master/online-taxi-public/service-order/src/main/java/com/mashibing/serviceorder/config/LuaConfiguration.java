package com.mashibing.serviceorder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {

    @Bean(name = "redisSetScript")
    public DefaultRedisScript<Boolean> redisSetScript(){
        DefaultRedisScript<Boolean> redisSetScript = new DefaultRedisScript<>();

        redisSetScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/redis-set.lua")));
        redisSetScript.setResultType(Boolean.class);
        return redisSetScript;
    }

    @Bean(name = "redisDelScript")
    public DefaultRedisScript<Boolean> redisDelScript(){
        DefaultRedisScript<Boolean> redisDelScript = new DefaultRedisScript<>();
        redisDelScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("luascript/redis-del.lua")));
        redisDelScript.setResultType(Boolean.class);
        return redisDelScript;
    }


}
