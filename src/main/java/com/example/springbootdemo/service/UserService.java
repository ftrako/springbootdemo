package com.example.springbootdemo.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootdemo.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 *
 * @author chendajian
 * @version 1.0 2022-10-26 16:02
 **/
@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    /**
     * 登录
     *
     * @param name 账号
     * @param pwd  密码
     * @return token
     */
    public Map<String, Object> login(String name, String pwd) {
        Map<String, Object> map = new HashMap<>();

        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("username", name);
            //生成JWT令牌
            String token = JwtUtil.getToken(payload);
            map.put("state", true);
            map.put("token", token);
            map.put("msg", "认证成功");
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 登出
     *
     * @param token token
     * @return ok
     */
    public String logout(String name, String token) {
//        try {
//            DecodedJWT decode = JwtUtil.verify(token);
//            logger.debug("header=" + new String(Base64Utils.decodeFromString(decode.getHeader()), StandardCharsets.UTF_8));
//            logger.debug("payload=" + new String(Base64Utils.decodeFromString(decode.getPayload()), StandardCharsets.UTF_8));
//            logger.debug("sign=" + decode.getSignature());
//            logger.debug("token=" + decode.getToken());
//        } catch (TokenExpiredException expired) {
//            logger.error("name=" + name + " token expired");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "logout ok";
    }
}
