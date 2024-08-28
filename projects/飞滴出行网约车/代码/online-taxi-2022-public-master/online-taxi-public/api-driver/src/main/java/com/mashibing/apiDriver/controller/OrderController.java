package com.mashibing.apiDriver.controller;

import com.mashibing.apiDriver.service.ApiDriverOrderInfoService;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.util.JwtUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    ApiDriverOrderInfoService apiDriverOrderInfoService;

    /**
     * 司机抢单
     * @param order
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/grab")
    @GlobalTransactional
    public ResponseResult grab(@RequestBody OrderRequest order, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization");
        // 从token中获取司机信息
        TokenResult tokenResult = JwtUtils.parseToken(token);
        String identity = tokenResult.getIdentity();

        String driverPhone = tokenResult.getPhone();

        Long orderId = order.getOrderId();

        String receiveOrderCarLongitude = order.getReceiveOrderCarLongitude();
        String receiveOrderCarLatitude = order.getReceiveOrderCarLatitude();

        return apiDriverOrderInfoService.grap(driverPhone, orderId, receiveOrderCarLongitude, receiveOrderCarLatitude);
    }

    /**
     * 去接乘客
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult changeStatus(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.toPickUpPassenger(orderRequest);
    }

    /**
     * 到达乘客上车点
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地，行程终止
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-getoff")
    public ResponseResult passengerGetoff(@RequestBody OrderRequest orderRequest){
        return apiDriverOrderInfoService.passengerGetoff(orderRequest);
    }

    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId){
        return apiDriverOrderInfoService.cancel(orderId);
    }

    @GetMapping("/detail")
    public ResponseResult<OrderInfo> detail(Long orderId){
        return apiDriverOrderInfoService.detail(orderId);
    }

    @GetMapping("/current")
    public ResponseResult<OrderInfo> currentOrder(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        TokenResult tokenResult = JwtUtils.parseToken(authorization);
        String identity = tokenResult.getIdentity();
        if (!identity.equals(IdentityConstants.DRIVER_IDENTITY)){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String phone = tokenResult.getPhone();

        return apiDriverOrderInfoService.currentOrder(phone,IdentityConstants.DRIVER_IDENTITY);
    }
}
