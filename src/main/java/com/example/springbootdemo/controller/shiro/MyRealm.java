package com.example.springbootdemo.controller.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author chendajian
 * @version 1.0
 * 2022-11-01 10:21
 **/
public class MyRealm implements Realm {
    private static final Logger logger = LogManager.getLogger(MyRealm.class);

    @Override
    public String getName() {
        return "myrealm";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();  // 得到用户名
        String password = new String((char[]) token.getCredentials()); // 得到密码
        if (!"zhang".equals(username)) {
            throw new UnknownAccountException(); // 如果用户名错误
        }
        if (!"123".equals(password)) {
            throw new IncorrectCredentialsException(); // 如果密码错误
        }
        // 如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
