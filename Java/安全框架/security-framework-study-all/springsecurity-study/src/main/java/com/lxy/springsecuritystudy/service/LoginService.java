package com.lxy.springsecuritystudy.service;

import com.lxy.springsecuritystudy.model.entity.User;


/**
* @author AngryYY
* @description
* @createDate 2024-08-19 19:54:19
*/
public interface LoginService  {
    String login(User user);

    boolean logout();
}
