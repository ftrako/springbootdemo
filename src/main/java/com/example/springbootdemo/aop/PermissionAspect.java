package com.example.springbootdemo.aop;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootdemo.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * 权限切面，通过权限控制拦截请求
 *
 * @author chendajian
 * @version 1.0 2022-10-26 17:27
 **/
@Component
@Aspect
@Order(1)
public class PermissionAspect {
    private static final Logger logger = LogManager.getLogger(PermissionAspect.class);

    // 定义一个切面，括号内写入第1步中自定义注解的路径
//    @Pointcut("execution(* com.example.springbootdemo..*(..))")
    // 定义切点
    @Pointcut("@annotation(com.example.springbootdemo.annotation.PermissionsAnnotation)")
    private void permissionCheck() {
    }

    // 定义处理
    @Around("permissionCheck()")
    public String permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 获取到请求的属性
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            // 获取到请求对象
            HttpServletRequest request = attributes.getRequest();

            // URL：根据请求对象拿到访问的地址
            logger.info("url={}, method={}, ip={}", request.getRequestURL(), request.getMethod(), request.getRemoteAddr());

//            // 获取请求的方法，是Get还是Post请求
//            logger.info("method=" + request.getMethod());
//
//            // ip：获取到访问
//            logger.info("ip=" + request.getRemoteAddr());

            // 头部带token
            String token = request.getHeader("token");

            //获取被拦截的类名和方法名
            //获取请求参数，详见接口类
//            Object[] objects = joinPoint.getArgs();
//            logger.debug("len(s)=" + objects.length);
//            logger.debug("obj1=" + objects[0]);
//            logger.debug("obj2=" + objects[1]);

            DecodedJWT decode = JwtUtil.verify(token);
//            logger.debug("header=" + new String(Base64Utils.decodeFromString(decode.getHeader()), StandardCharsets.UTF_8));
            logger.debug("request payload=" + new String(Base64Utils.decodeFromString(decode.getPayload()), StandardCharsets.UTF_8));
//            logger.debug("sign=" + decode.getSignature());
//            logger.debug("token=" + decode.getToken());
        } catch (TokenExpiredException expired) {
            logger.error("fail permission, token expired");
            return "fail permission";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail check permission";
        }

        return (String) joinPoint.proceed();
    }
}

