package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.core.exception.BaseException;
import com.msb.hjycommunity.common.core.exception.CustomException;
import com.msb.hjycommunity.common.core.exception.UserPasswordNotMatchException;
import com.msb.hjycommunity.common.utils.RedisCache;
import com.msb.hjycommunity.common.core.exception.CaptchaNotMatchException;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.service.SysLoginService;
import com.msb.hjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author spikeCong
 * @date 2023/5/5
 **/
@Component
public class SysLoginServiceImpl implements SysLoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 带验证码登录
     * @param username
     * @param password
     * @param code
     * @param uuid
     * @return: java.lang.String
     */
    @Override
    public String login(String username, String password, String code, String uuid) {

        //1.从redis中获取验证码,判断是否正确
        String key = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(key);
        redisCache.deleteObject(key);

        if(captcha == null || !code.equalsIgnoreCase(captcha)){
            throw new CaptchaNotMatchException();
        }

        //2.进行用户认证
        Authentication authentication = null;
        try{
            authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (Exception e){
            throw new CustomException(400,"用户不存在/密码错误");
        }

        //3.获取用户经过身份验证的用户的主体信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        return tokenService.createToken(loginUser);
    }
}
