package com.example.springbootdemo.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootdemo.controller.api.FileController;
import com.example.springbootdemo.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

/**
 * 权限切面
 *
 * @author chendajian
 * @version 1.0 2022-10-26 17:27
 **/
@Component
@Aspect
@Order(1)
public class PermissionAdvice {
    private static final Logger logger = LogManager.getLogger(PermissionAdvice.class);

    // 定义一个切面，括号内写入第1步中自定义注解的路径
//    @Pointcut("execution(* com.example.springbootdemo..*(..))")
    @Pointcut("@annotation(com.example.springbootdemo.annotation.PermissionsAnnotation)")
    private void permissionCheck() {
    }

    @Around("permissionCheck()")
    public String permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取请求参数，详见接口类
        Object[] objects = joinPoint.getArgs();
        logger.debug("len(s)=" + objects.length);
        logger.debug("obj1=" + objects[0]);
        logger.debug("obj2=" + objects[1]);

        try {
            DecodedJWT decode = JwtUtil.verify(objects[1].toString());
            logger.debug("header=" + new String(Base64Utils.decodeFromString(decode.getHeader()), StandardCharsets.UTF_8));
            logger.debug("payload=" + new String(Base64Utils.decodeFromString(decode.getPayload()), StandardCharsets.UTF_8));
            logger.debug("sign=" + decode.getSignature());
            logger.debug("token=" + decode.getToken());
        } catch (TokenExpiredException expired) {
            logger.error("fail permission, name=" + objects[0] + " token expired");
            return "fail permission";
        } catch (Exception e) {
            e.printStackTrace();
            return "exception:"+e.toString();
        }

        return (String)joinPoint.proceed();
    }
}

