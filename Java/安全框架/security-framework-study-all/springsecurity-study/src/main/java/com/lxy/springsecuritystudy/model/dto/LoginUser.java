package com.lxy.springsecuritystudy.model.dto;

import com.lxy.springsecuritystudy.model.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户登录信息封装类，实现了 Spring Security 的 UserDetails 接口，用于存储用户的认证和授权信息。
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 20:59
 * @Description：包含用户实体和用户权限列表，提供用户认证时的基本信息。
 */
@Data
public class LoginUser implements UserDetails {
    private User user;  // 用户实体对象，用于获取用户详细信息

    /**
     * 构造函数，接收一个用户实体作为参数。
     * @param user 用户实体对象
     */
    public LoginUser(User user) {
        this.user = user;
    }

    /**
     * 获取用户的权限列表。
     * @return 用户的权限集合，当前实现返回一个空列表。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();  // 返回空的权限列表
    }

    /**
     * 获取用户的密码。
     * @return 用户密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户的用户名。
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    /**
     * 账户是否未过期。
     * @return true 表示未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定。
     * @return true 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证是否未过期。
     * @return true 表示凭证未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否启用。
     * @return true 表示启用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
