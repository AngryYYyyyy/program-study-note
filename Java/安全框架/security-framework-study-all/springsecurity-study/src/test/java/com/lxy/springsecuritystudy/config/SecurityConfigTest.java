package com.lxy.springsecuritystudy.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 22:20
 * @Description：
 */
@SpringBootTest
class SecurityConfigTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void testPasswordEncoder() {
        String encode = passwordEncoder.encode("020516");
        System.out.println(encode);
    }
}