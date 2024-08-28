package com.mashibing.internalcommon.request;

import lombok.Data;
// todo 这个类 以后不做为公共类了。
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

    private String driverPhone;
}