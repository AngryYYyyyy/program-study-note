package com.mashibing.internalcommon.request;

import lombok.Data;

@Data
public class DriverGrabRequest {

    private Long orderId;

    private String receiveOrderCarLongitude;

    private String receiveOrderCarLatitude;

    private Long carId;

    private Long driverId;

    private String licenseId;

    private String vehicleNo;

    private String vehicleType;

    private String driverPhone;


}
