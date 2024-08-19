package com.lxy.springsecuritystudy.mapper;

import com.lxy.springsecuritystudy.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 21:19
 * @Description：
 */
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void test() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}