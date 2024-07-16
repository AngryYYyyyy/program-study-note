package com.msb.hjycommunity.system.service.impl;

import com.msb.hjycommunity.common.constant.Constants;
import com.msb.hjycommunity.common.utils.RedisCache;
import com.msb.hjycommunity.common.utils.UUIDUtils;
import com.msb.hjycommunity.system.domain.LoginUser;
import com.msb.hjycommunity.system.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author spikeCong
 * @date 2023/5/5
 **/
@Component
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisCache redisCache;

    //令牌自定义标识
    @Value("${token.header}")
    private String header;

    //令牌秘钥
    @Value("${token.secret}")
    private String secret;

    //令牌有效期
    @Value("${token.expireTime}")
    private int expireTime;

    //毫秒
    private static final long MILLIS_SECOND = 1000;

    //分钟
    private static final long MILLIS_MINUTE = MILLIS_SECOND * 60;

    //20分钟
    private static final long MILLIS_MINUTE_TEN = MILLIS_SECOND * 60 * 20;

    /**
     * 创建令牌
     * @param loginUser
     * @return: java.lang.String
     */
    @Override
    public String createToken(LoginUser loginUser) {

        //设置用户的唯一标识
        String userKey = UUIDUtils.randomUUID();
        loginUser.setToken(userKey);

        //todo 保存用户信息 刷新令牌
        refreshToken(loginUser);

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY,userKey);

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        return token;
    }

    @Override
    public void refreshToken(LoginUser loginUser) {

        loginUser.setLoginTime(System.currentTimeMillis());
        //过期时间30分钟
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);

        //根据UUID将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey,loginUser,expireTime, TimeUnit.MINUTES);
    }

    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if(!StringUtils.isEmpty(token)){
            Claims claims = parseToken(token);
            //解析对应的用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            LoginUser loginUser = redisCache.getCacheObject(userKey);

            return loginUser;
        }
        return null;
    }

    @Override
    public void verifyToken(LoginUser loginUser) {
        Long expireTime = loginUser.getExpireTime();
        long currentTimeMillis = System.currentTimeMillis();
        //相差不足20分钟,自动刷新缓存
        if(expireTime - currentTimeMillis <= MILLIS_MINUTE_TEN){
            refreshToken(loginUser);
        }
    }

    @Override
    public void setLoginUser(LoginUser loginUser) {
        if(!Objects.isNull(loginUser) && !StringUtils.isEmpty(loginUser.getToken())){
            refreshToken(loginUser);
        }
    }

    @Override
    public void delLoginUser(String token) {
        if(!StringUtils.isEmpty(token)){
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 从令牌中获取数据声明
     * @param token
     * @return: java.lang.String
     */
    private Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //拼接tokenKey
    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 从请求头中获取 token
     * @param request
     * @return: java.lang.String
     */
    private String getToken(HttpServletRequest request){
        String token = request.getHeader(this.header);
        //JWT 标准写法 Authorization: Bearer aaa.bb.cc
        if(!StringUtils.isEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)){
            token = token.replace(Constants.TOKEN_PREFIX,"");
        }
        return token;
    }


}
