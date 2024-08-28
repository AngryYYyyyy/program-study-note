package com.mashibing.serviceorder.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.DriverGrabRequest;

public interface GrabService {

    public ResponseResult grab(DriverGrabRequest driverGrabRequest) ;
}
