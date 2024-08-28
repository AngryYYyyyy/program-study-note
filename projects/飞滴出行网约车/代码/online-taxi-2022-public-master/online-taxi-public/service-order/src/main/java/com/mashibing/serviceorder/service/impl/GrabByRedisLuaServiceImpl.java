package com.mashibing.serviceorder.service.impl;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service("grabByRedisLuaService")
public class GrabByRedisLuaServiceImpl implements GrabService {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RenewRedisLock renewRedisLock;

    @Autowired
    @Qualifier("redisSetScript")
    DefaultRedisScript<Boolean> redisSetScript;

    @Autowired
    @Qualifier("redisDelScript")
    DefaultRedisScript<Boolean> redisDelScript;

    @Override
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {
        ResponseResult grab = null;
        String orderId = driverGrabRequest.getOrderId()+"";
        String driverId = driverGrabRequest.getDriverId()+"";
        String key = orderId;
        String value = driverId +"-"+ UUID.randomUUID();
        // 设置加锁的key,设置过期时间，避免死锁 -lua

        List<String> strings = Arrays.asList(key, value);
        Boolean aBoolean = stringRedisTemplate.execute(redisSetScript, strings, "30");

        if (aBoolean){
            renewRedisLock.renewRedisLock(key,value,20);
            System.out.println("开始锁redis diy");


            grab = orderInfoService.grab(driverGrabRequest);
            System.out.println("结束锁redis diy");

            List<String> keyArg = Arrays.asList(key);
            Boolean dBoolean = stringRedisTemplate.execute(redisDelScript, keyArg, value);
            System.out.println("删除："+dBoolean);

        }else {
            grab = ResponseResult.fail(CommonStatusEnum.ORDER_GRABING.getCode(),CommonStatusEnum.ORDER_GRABING.getValue());
        }

        return grab;
    }
}