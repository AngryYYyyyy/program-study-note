package com.lxy.springsecuritystudy.controller;

import com.lxy.springsecuritystudy.utils.RedisCache;
import com.lxy.springsecuritystudy.utils.UUIDUtils;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/20 14:09
 * @Description：
 */
@RestController
public class CaptchaController {
    private static final String CAPTCHA_CODE_KEY = "captchaCode:";
    @Autowired
    private RedisCache redisCache;
    @GetMapping("/captcha")
    public ResponseEntity<Map<String,String>> createCodeImage() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);

        //生成验证码,及验证码唯一标识
        String uuid = UUIDUtils.simpleUUID();
        String key = CAPTCHA_CODE_KEY + uuid;
        String captcha = specCaptcha.text().toLowerCase();

        //保存到redis
        redisCache.setCacheObject(key, captcha, 1000, TimeUnit.SECONDS);
        // 创建响应map
        Map<String, String> response = new HashMap<>();
        response.put("uuid", uuid);
        response.put("captcha", specCaptcha.toBase64());

        return ResponseEntity.ok(response);
    }
}
