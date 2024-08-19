package com.lxy.integratingmybatis.service.impl;

import com.lxy.integratingmybatis.mapper.UserMapper;
import com.lxy.integratingmybatis.model.entity.User;
import com.lxy.integratingmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author AngryYY
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-08-19 10:31:15
*/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User createUser(User user) {
        userMapper.insertUser(user);
        return user;  // Assume the user is returned with an ID if using an auto-increment field
    }

    public User getUserById(Integer id) {
        return userMapper.selectUserById(id);
    }

    public User updateUser(User user) {
        userMapper.updateUser(user);
        return user;
    }

    public boolean deleteUser(Integer id) {
        return userMapper.deleteUserById(id) > 0;
    }

    public List<User> getAllUsers() {
        return userMapper.findAllUsers();
    }
}




