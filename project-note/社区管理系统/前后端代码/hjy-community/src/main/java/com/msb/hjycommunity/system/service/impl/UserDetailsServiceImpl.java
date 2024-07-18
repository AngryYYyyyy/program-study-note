package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.core.exception.BaseException;
import com.msb.hjycommunity.common.enums.UserStatus;
import com.msb.hjycommunity.framework.service.SysPermissionService;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.domain.SysUser;
import com.msb.hjycommunity.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

/**
 * 用户验证处理
 * @author spikeCong
 * @date 2023/5/5
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUser user = sysUserService.selectUserByUserName(username);

        if(Objects.isNull(user)){
            log.info("登录用户: {} 不存在",username);
            throw new UsernameNotFoundException("登录用户: " + username +" 不存在");
        }
        else if(UserStatus.DELETED.getCode().equals(user.getDelFlag())){
            log.info("登录用户: {} 已被删除",username);
            throw new BaseException("对不起,您的账号: " + username + " 已被删除");
        }
        else if(UserStatus.DISABLE.getCode().equals(user.getStatus())){
            log.info("登录用户: {} 已被停用",username);
            throw new BaseException("对不起,您的账号: " + username + " 已被停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user){
        Set<String> menuPermission = permissionService.getMenuPermission(user);
        return new LoginUser(user,menuPermission);
    }
}
