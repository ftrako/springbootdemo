package com.example.springbootdemo.controller.api;

import com.example.springbootdemo.service.UserService;
import com.example.springbootdemo.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chendajian
 * @version 1.0 2022-10-26 16:23
 **/
@RestController
@RequestMapping("/v1")
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param name 账号
     * @param pwd  密码
     * @return token
     */
    @GetMapping("/login")
    public Map<String, Object> login(String name, String pwd) {
        logger.debug("name=" + name + ", pwd=" + pwd);
        return userService.login(name, pwd);
    }

    /**
     * 登录
     *
     * @param name  账号
     * @param token token
     * @return ok
     */
    @GetMapping("/logout")
    public String logout(String name, String token) {
        logger.debug("token=" + token);
        return userService.logout(name, token);
    }
}
