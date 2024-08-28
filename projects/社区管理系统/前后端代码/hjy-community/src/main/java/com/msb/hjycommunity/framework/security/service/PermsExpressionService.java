package com.msb.hjycommunity.framework.security.service;

import com.msb.hjycommunity.common.utils.ServletUtils;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.domain.SysRole;
import com.msb.hjycommunity.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义权限校验规则
 * @author spikeCong
 * @date 2023/5/12
 **/
@Component("pe")
public class PermsExpressionService {

    private static final String ALL_PERMISSION = "*:*:*";

    @Autowired
    private TokenService tokenService;

    /**
     * 1) 验证用户是否具备某一个权限
     * @param permission
     * @return: boolean
     */
    public boolean hasPerms(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
            return false;
        }

        return hasPermissions(loginUser.getPermissions(),permission);
    }

    /**
     * 判断是否包含权限
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return: boolean
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(permission) || permissions.contains(ALL_PERMISSION);
    }

    /**
     * 验证用户是否有任意一个权限
     * @param permissions
     * @return: boolean
     */
    public boolean hasAnyPerms(String permissions){
        if(StringUtils.isEmpty(permissions)){
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())){
            return false;
        }

        Set<String> authorities = loginUser.getPermissions();
        for (String perms : permissions.split(",")) {
            if(perms != null && hasPermissions(authorities,perms)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     * @param role
     * @return: boolean
     */
    public boolean hasRole(String role){
        if(StringUtils.isEmpty(role)){
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())){
            return false;
        }
        for (SysRole sysRole : loginUser.getUser().getRoles()) {
            String roleKey = sysRole.getRoleKey();
            if("admin".equals(roleKey) || roleKey.equals(role)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有任意一个角色
     * @param roles
     * @return: boolean
     */
    public boolean hasAnyRole(String roles){
        if(StringUtils.isEmpty(roles)){
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if(Objects.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())){
            return false;
        }
        for (String role : roles.split(",")) {
            if(hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
