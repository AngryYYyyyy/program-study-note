package com.lxy.mybatisplusstudyall.mapper;

import com.lxy.mybatisplusstudyall.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/20 20:33
 * @Description：
 */
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    void listUser() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
}