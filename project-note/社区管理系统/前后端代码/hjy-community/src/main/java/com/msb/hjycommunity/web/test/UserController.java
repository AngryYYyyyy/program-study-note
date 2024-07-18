package com.msb.hjycommunity.web.test;

import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.exception.BaseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author spikeCong
 * @date 2023/3/1
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/queryUserById")
    public BaseResponse<User> queryUserById(String userId){

        if(userId != null){
           return BaseResponse.success(new User(userId,"spike"));
        }else{
            return BaseResponse.fail("查询用户信息失败！");
        }
    }

    @RequestMapping("/addUser")
    public BaseResponse addUser(@Validated User user, BindingResult bindingResult){

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        //如果参数校验失败，会将错误信息封装成对象组装到BindingResult
        if(!fieldErrors.isEmpty()){
            return BaseResponse.fail(fieldErrors.get(0).getDefaultMessage());
        }

        return BaseResponse.success("OK。。。");
    }


    @RequestMapping("/queryUser")
    public BaseResponse queryUser(User user){

        throw new BaseException("500","测试异常类！！！");
    }
}
