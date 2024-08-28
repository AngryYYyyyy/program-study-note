package com.msb.hjycommunity.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.utils.ServletUtils;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义退出登录处理器
 * @author spikeCong
 * @date 2023/5/17
 **/
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LoginUser loginUser = tokenService.getLoginUser(request);
        if(!Objects.isNull(loginUser)){
            String token = loginUser.getToken();
            //删除用户缓存数据
            tokenService.delLoginUser(token);
        }

        ServletUtils.renderString(response, JSON.toJSONString(BaseResponse.success("登出成功")));
    }
}
