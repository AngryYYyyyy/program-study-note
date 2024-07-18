package com.msb.hjycommunity.system.service;

/**
 * @author spikeCong
 * @date 2023/5/5
 **/
public interface SysLoginService {

    public String login(String username,String password,String code,String uuid);
}
