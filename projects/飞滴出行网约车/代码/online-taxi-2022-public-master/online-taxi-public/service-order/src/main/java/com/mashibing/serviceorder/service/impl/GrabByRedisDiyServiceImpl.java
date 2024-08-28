package com.mashibing.serviceorder.service.impl;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("grabByRedisDiyService")
public class GrabByRedisDiyServiceImpl implements GrabService {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RenewRedisLock renewRedisLock;

    @Override
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {
        ResponseResult grab = null;
        String orderId = driverGrabRequest.getOrderId()+"";
        String driverId = driverGrabRequest.getDriverId()+"";
        String key = orderId;
        String value = driverId +"-"+ UUID.randomUUID();
        // 设置加锁的key,设置过期时间，避免死锁
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent(key, value,20, TimeUnit.SECONDS);

        if (aBoolean){
            renewRedisLock.renewRedisLock(key,value,20);
            System.out.println("开始锁redis diy");
            try {
                TimeUnit.SECONDS.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            grab = orderInfoService.grab(driverGrabRequest);
            System.out.println("结束锁redis diy");

            String s = stringRedisTemplate.opsForValue().get(key);
            if (s.equals(value)){
                stringRedisTemplate.delete(key);
            }

        }else {
            grab = ResponseResult.fail(CommonStatusEnum.ORDER_GRABING.getCode(),CommonStatusEnum.ORDER_GRABING.getValue());
        }

        return grab;
    }
}