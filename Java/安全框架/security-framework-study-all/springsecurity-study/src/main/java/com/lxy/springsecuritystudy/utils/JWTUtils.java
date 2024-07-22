package com.lxy.springsecuritystudy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * JWT工具类，用于处理JWT（JSON Web Token）的生成和解析。
 *
 * @author 作者：AngryYYYYYY
 * @version 1.0
 * @since 创建时间：2024年7月23日00:24
 */
public class JWTUtils {
    public static final String secretString = "lxy";
    public static Key getSecretKey() {
        return new SecretKeySpec(secretString.getBytes(), "HmacSHA1");
    }

    /**
     * 创建并返回一个带有预定义声明的JWT令牌。
     *
     * @return 签名后的JWT令牌字符串
     */
    public static String createToken() {
        Date expTime = new Date(System.currentTimeMillis() + 3600 * 1000);  // 令牌过期时间，从现在开始1小时后

        JwtBuilder builder = Jwts.builder()
                .setId("9527")  // 设置唯一标识
                .setSubject("demo")  // 设置主题
                .setIssuedAt(new Date())  // 设置签发时间
                .setExpiration(expTime)  // 设置过期时间
                .claim("role", "admin")  // 自定义声明
                .signWith(SignatureAlgorithm.HS256, getSecretKey());  // 设置签名使用HS256算法，并设置密钥
        // 将JWT压缩为字符串形式
        builder.compressWith()
        return builder.compact();
    }

    /**
     * 解析JWT令牌，并打印声明信息。
     *
     * @param token 要解析的JWT令牌字符串
     */
    public static Claims parse(String token) {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSecretKey())  // 设置解析时使用的密钥
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
    }
}
