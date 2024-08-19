package com.lxy.springsecuritystudy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

/**
 * JWT工具类，用于生成和解析JSON Web Tokens（JWT）。
 * 本类提供了根据指定用户名和过期时间生成JWT令牌的方法，
 * 以及解析令牌以提取信息的方法。
 */
public class JwtUtils {

    // 用于签名JWT令牌的密钥。请将"your_secret_key"替换为一个强大的、私有的密钥。
    private static final String SECRET_KEY = "your_secret_key";
    //毫秒转分钟
    private static final long MINUTES = 1000*60;
    //默认过期时间30分钟
    private static final long EXPIRED_TIME = 30*MINUTES;

    /**
     * 生成JWT令牌。
     * @param userId 用户id，通常是用户的唯一标识。
     * @return 生成的JWT令牌字符串。
     */
    public static String createToken(String userId) {
        return createToken(userId,EXPIRED_TIME);
    }
    /**
     * 生成JWT令牌。
     * @param userId 用户id，通常是用户的唯一标识。
     * @param expirationMillis 令牌过期时间，以毫秒为单位。
     * @return 生成的JWT令牌字符串。
     */
    public static String createToken(String userId, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        return Jwts.builder()
                .setSubject(userId) // 设置主题，即令牌的所有者。
                .setIssuedAt(now)     // 设置令牌的签发时间。
                .setExpiration(new Date(nowMillis + expirationMillis)) // 设置令牌的过期时间。
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用HS256算法和密钥进行签名。

                .compact();
    }

    /**
     * 解析JWT令牌。
     * @param token 要解析的JWT令牌字符串。
     * @return Claims对象，包含令牌中的声明信息。
     * @throws io.jsonwebtoken.JwtException 如果令牌无效或解析过程中出现问题。
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置用于解析JWT的签名密钥。
                .parseClaimsJws(token)    // 解析传入的令牌。
                .getBody();
    }
}

