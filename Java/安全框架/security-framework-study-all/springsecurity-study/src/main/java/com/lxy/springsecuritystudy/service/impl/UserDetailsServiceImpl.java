package com.lxy.springsecuritystudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxy.springsecuritystudy.mapper.UserMapper;
import com.lxy.springsecuritystudy.model.entity.User;
import com.lxy.springsecuritystudy.model.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自定义用户详情服务实现，用于根据用户名加载用户详细信息。
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 21:09
 * @Description：通过数据库查询用户信息，如果用户存在则返回用户详情，否则抛出异常。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;  // 用户数据库映射器，用于执行数据库查询

    /**
     * 根据用户名加载用户的详细信息。
     * @param username 用户输入的用户名
     * @return UserDetails 用户详细信息
     * @throws UsernameNotFoundException 如果用户名不在数据库中，则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 创建查询包装器，设置查询条件为用户名匹配
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);

        // 从数据库中查询用户
        User user = userMapper.selectOne(wrapper);

        // 检查是否找到用户，如果没有找到则抛出异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("未找到该用户");
        }

        // 如果找到用户，则返回封装在 LoginUser 类中的用户详情
        return new LoginUser(user);
    }
}

