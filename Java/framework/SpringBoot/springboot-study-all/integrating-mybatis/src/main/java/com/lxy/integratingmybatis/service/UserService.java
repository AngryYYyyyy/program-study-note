package com.lxy.integratingmybatis.service;


import com.lxy.integratingmybatis.model.entity.User;

import java.util.List;

/**
* @author AngryYY
* @description 针对表【user】的数据库操作Service
* @createDate 2024-08-19 10:31:15
*/
public interface UserService  {
    User createUser(User user);

    User getUserById(Integer id);

    User updateUser(User user);

    boolean deleteUser(Integer id);
    List<User> getAllUsers();
}
