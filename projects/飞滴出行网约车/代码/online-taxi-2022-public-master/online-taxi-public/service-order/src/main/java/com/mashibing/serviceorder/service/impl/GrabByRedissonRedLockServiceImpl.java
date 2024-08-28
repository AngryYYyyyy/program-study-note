package com.mashibing.serviceorder.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("grabByRedissonRedLockService")
public class GrabByRedissonRedLockServiceImpl implements GrabService {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    @Qualifier("redissonClient1")
    RedissonClient redissonClient1;

    @Autowired
    @Qualifier("redissonClient2")
    RedissonClient redissonClient2;

    @Autowired
    @Qualifier("redissonClient3")
    RedissonClient redissonClient3;

    @Autowired
    @Qualifier("redissonClient4")
    RedissonClient redissonClient4;

    @Autowired
    @Qualifier("redissonClient5")
    RedissonClient redissonClient5;

    @Override
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {

        String orderId = driverGrabRequest.getOrderId()+"";

        String key = orderId;

        // 红锁
        RLock rLock1 = redissonClient1.getLock(key);
        RLock rLock2 = redissonClient2.getLock(key);
        RLock rLock3 = redissonClient3.getLock(key);
        RLock rlock4 = redissonClient4.getLock(key);
        RLock rlock5 = redissonClient5.getLock(key);

        RedissonRedLock lock = new RedissonRedLock(rLock1, rLock2, rLock3, rlock4, rlock5);
        lock.lock();

        System.out.println("开始锁redis redisson cluster yaml");
        try {
            TimeUnit.SECONDS.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseResult grab = orderInfoService.grab(driverGrabRequest);
        System.out.println("结束锁redis redisson cluster yaml");
        lock.unlock();

        return grab;
    }
}