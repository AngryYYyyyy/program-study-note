package com.lxy.springsecuritystudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxy.springsecuritystudy.mapper.UserMapper;
import com.lxy.springsecuritystudy.model.entity.User;
import com.lxy.springsecuritystudy.model.dto.LoginUser;
import com.lxy.springsecuritystudy.service.LoginService;
import com.lxy.springsecuritystudy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.lxy.springsecuritystudy.utils.JwtUtils;
import java.util.Objects;

/**
 * 登录服务实现类，提供了基于用户名和密码的用户认证方法，并通过 JWT 管理会话。
 * @Author AngryYY
 * @description 自定义认证实现，验证登录凭证并使用 JWT 管理会话。
 * @createDate 2024-08-19 19:54:19
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;  // 管理用户认证的对象

    @Autowired
    private RedisCache redisCache;  // 用于存储用户会话信息的 Redis 缓存

    @Autowired
    private UserMapper userMapper;  // 用户数据访问对象

    /**
     * 处理登录请求，通过认证用户并返回一个 JWT 令牌。
     * @param user 要认证的用户，包含用户名和密码。
     * @return String 如果认证成功，返回 JWT 令牌。
     */
    @Override
    public String login(User user) {
        // 尝试使用提供的用户名和密码进行认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        // 检查认证结果，如果认证失败则抛出异常
        if (Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证成功，从数据库中检索完整的用户信息
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", user.getUserName());
        user = userMapper.selectOne(wrapper);

        // 生成并返回 JWT
        String userId = user.getUserId().toString();
        String jwt = JwtUtils.createToken(userId);

        // 将用户详情存储在 Redis 中，以便通过 JWT 管理会话
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String redisKey = "login:" + userId;
        redisCache.setCacheObject(redisKey, loginUser);

        return jwt;
    }


    @Override
    public boolean logout() {
        // 获取当前用户的认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 检查是否存在认证信息，如果不存在或主体为空，则抛出异常
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("用户未登录或已过期");
        }

        // 从认证信息中获取当前登录的用户详情
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getUserId();

        // 删除Redis中存储的用户信息，通过用户ID构建Redis的键名
        boolean isDeleted = redisCache.deleteObject("login:" + userId);

        // 清除SecurityContextHolder中的认证信息，确保注销完全
        SecurityContextHolder.clearContext();

        // 返回Redis删除操作的结果，如果成功删除，则返回true，否则返回false
        return isDeleted;
    }

}
