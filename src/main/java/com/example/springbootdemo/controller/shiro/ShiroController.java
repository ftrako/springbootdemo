package com.example.springbootdemo.controller.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chendajian
 * @version 1.0 2022-11-01 09:24
 **/
@RestController
@RequestMapping("/v1/shiro")
public class ShiroController {
    private static final Logger logger = LogManager.getLogger(ShiroController.class);

    @GetMapping("/login")
    public String login(String name, String pwd) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            logger.error("fail authenticate, name=" + name, ", pwd=" + pwd);
            return "fail";
        }
//        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
        //6、退出
        subject.logout();
        return "ok";
    }

    @GetMapping("/login-realm")
    public String loginRealm(String name, String pwd) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-realm.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);

        try {
            //4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            logger.error("fail authenticate-realm, name=" + name, ", pwd=" + pwd);
            return "fail";
        }
        //6、退出
        subject.logout();
        return "ok";
    }

    @GetMapping("/login-permission")
    public String loginPermission(String name, String pwd, String role, String permission) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-permission.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);

        try {
            //4、登录，即身份验证
            subject.login(token);
            logger.debug("{} has {} => {}", name, role, subject.hasRole(role));
            subject.checkRole(role);
            subject.checkPermission(permission);
        } catch (AuthenticationException e) {
            //5、身份验证失败
            logger.error("fail authenticate-permission, name=" + name, ", pwd=" + pwd);
            return "fail";
        } catch (AuthorizationException e) {
            //5、没有权限
            logger.error("no permission, name=" + name, ", pwd=" + pwd);
            return "fail";
        }
        //6、退出
        subject.logout();
        return "ok";
    }

    @GetMapping("/login-auth")
    public String loginAuth(String name, String pwd, String role) {
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro/shiro-authorizer.ini");
        //2、得到SecurityManager实例 并绑定给SecurityUtils
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);

        try {
            //4、登录，即身份验证
            subject.login(token);

            //判断拥有权限：user:create
            logger.debug(subject.isPermitted("user1:update"));
            logger.debug(subject.isPermitted("user2:update"));

            //通过二进制位的方式表示权限
            logger.debug("+user1+2:" + subject.isPermitted("+user1+2"));//新增权限
            logger.debug("+user1+8:" + subject.isPermitted("+user1+8"));//查看权限
            logger.debug("+user2+10:" + subject.isPermitted("+user2+10"));//新增及查看
            logger.debug("+user1+4:" + subject.isPermitted("+user1+4"));//没有删除权限
            logger.debug("menu:view:" + subject.isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
        } catch (AuthenticationException | AuthorizationException e) {
            //5、身份验证失败
            logger.error("fail authenticate-permission, name=" + name, ", pwd=" + pwd);
            return "fail";
        }
        //6、退出
        subject.logout();
        return "ok";
    }
}
