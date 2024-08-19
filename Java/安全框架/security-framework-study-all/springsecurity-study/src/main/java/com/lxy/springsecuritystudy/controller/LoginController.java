package com.lxy.springsecuritystudy.controller;

import com.lxy.springsecuritystudy.model.entity.User;
import com.lxy.springsecuritystudy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器，处理应用的登录请求。
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 19:55
 * @Description：提供登录接口，接收用户的登录信息并返回JWT令牌。
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;  // 注入登录服务，用于业务逻辑处理

    /**
     * 处理用户登录请求。
     * 用户通过提交用户名和密码来请求登录，如果认证成功，返回一个JWT令牌。
     * @param user 用户登录信息，封装在 User 对象中
     * @return ResponseEntity 返回包含JWT令牌的响应实体
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // 调用登录服务，进行用户认证，并接收返回的JWT令牌
            String jwt = loginService.login(user);

            // 检查JWT是否成功生成
            if (jwt == null || jwt.isEmpty()) {
                // 未认证成功，返回401状态码
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }

            // 创建响应实体，包含JWT令牌，并返回
            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            // 处理异常情况，返回500状态码
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    /**
     * 退出登录接口，删除用户会话
     * @return 返回注销操作结果
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            // 尝试执行注销逻辑
            if (loginService.logout()) {
                // 注销成功，返回成功响应
                return ResponseEntity.ok().body("注销成功");
            } else {
                // 注销逻辑执行了，但未达到预期效果，可能是因为用户已经处于未登录状态
                return ResponseEntity.badRequest().body("注销失败，用户可能已经是未登录状态");
            }
        } catch (Exception e) {
            // 注销过程中发生异常，返回内部服务器错误响应
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器内部错误，注销失败");
        }
    }

}
