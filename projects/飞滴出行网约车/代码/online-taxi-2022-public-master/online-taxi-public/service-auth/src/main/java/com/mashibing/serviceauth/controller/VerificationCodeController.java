package com.mashibing.serviceauth.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceauth.request.CheckVerificationCodeDTO;
import com.mashibing.serviceauth.request.SendVerificationCodeDTO;
import com.mashibing.serviceauth.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification-code")
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 发送验证码
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/generator")
    public ResponseResult verificationCode(@Validated @RequestBody SendVerificationCodeDTO verificationCodeDTO) {

        String phone = verificationCodeDTO.getPhone();
        String identity = verificationCodeDTO.getIdentity();

        return verificationCodeService.generatorCode(phone,identity);

    }

    @PostMapping("/check")
    public ResponseResult checkVerificationCode(@Validated @RequestBody CheckVerificationCodeDTO checkVerificationCodeDTO) {

        String phone = checkVerificationCodeDTO.getPhone();
        String identity = checkVerificationCodeDTO.getIdentity();
        String verificationCode = checkVerificationCodeDTO.getVerificationCode();

        return verificationCodeService.checkCode(phone,verificationCode, identity);

    }
}