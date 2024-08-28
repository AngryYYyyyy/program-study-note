package com.mashibing.serviceorder.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RenewRedisLock {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Async
    public void renewRedisLock(String key, String value, int timePeriod){
        System.out.println("异步加锁");
        String s = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(s) && s.equals(value)){
            // 30s,当加好锁的时候，10s 续期一次。
            int renewPeriod = timePeriod / 3;

            try {
                TimeUnit.SECONDS.sleep(renewPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stringRedisTemplate.expire(key, timePeriod, TimeUnit.SECONDS);
        }else {
            return;
        }
        renewRedisLock(key,value,timePeriod);

    }
}
