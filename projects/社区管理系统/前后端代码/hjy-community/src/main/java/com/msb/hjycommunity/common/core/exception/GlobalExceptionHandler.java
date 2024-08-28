package com.msb.hjycommunity.common.core.exception;

import com.msb.hjycommunity.common.core.domain.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author spikeCong
 * @date 2023/3/1
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public BaseResponse baseExceptionHandler(BaseException e){

        return BaseResponse.fail(e.getDefaultMessage());
    }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(CustomException.class)
    public BaseResponse businessException(CustomException e){

        return BaseResponse.fail(e.getCode()+"",e.getMsg(),e.isSuccess());
    }
}
