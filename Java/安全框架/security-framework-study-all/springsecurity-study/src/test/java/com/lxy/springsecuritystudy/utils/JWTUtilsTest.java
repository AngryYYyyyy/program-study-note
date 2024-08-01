package com.lxy.springsecuritystudy.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/23 0:37
 * @Description：
 */
@SpringBootTest
class JWTUtilsTest {

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void jwt() {
        String token = JWTUtils.createToken("Angryyyyy",1000000);
        System.out.println(token);
        Claims parse = JWTUtils.parseToken(token);
        System.out.println(parse);
    }
}