package com.msb.hjycommunity.web.test;

import javax.validation.constraints.NotNull;

/**
 * @author spikeCong
 * @date 2023/3/1
 **/
public class User {

    @NotNull(message = "userId 不能为空")
    private String userId;

    private String username;

    public User() {
    }

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
