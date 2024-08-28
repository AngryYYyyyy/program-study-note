package com.mashibing.serviceauth.request;

import lombok.Data;

@Data
public class CheckVerificationCodeDTO {

    private String phone;

    private String verificationCode;

    private String identity;
}
