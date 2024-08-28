package com.mashibing.serviceorder.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;
import com.mashibing.serviceorder.service.GrabService;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("grabByMultiRedisService")
public class GrabByMultiRedisServiceImpl implements GrabService {

    @Autowired
    OrderInfoService orderInfoService;

    @Override
    public ResponseResult grab(DriverGrabRequest driverGrabRequest) {

        System.out.println("开始锁 multi  Redis");
        ResponseResult grab = orderInfoService.grab(driverGrabRequest);
        System.out.println("结束锁 multi Redis");

        return grab;
    }
}
