package com.example.springbootdemo.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 方法耗时切面，打印方法的耗时
 *
 * @author chendajian
 * @version 1.0 2022-10-26 17:27
 **/
@Component
@Aspect
@Order(1)
public class MethodCostAspect {
    private static final Logger logger = LogManager.getLogger(MethodCostAspect.class);

    // 定义切点
    @Pointcut("execution(* com.example.springbootdemo.controller..*(..))")
    private void fnc() {
    }

    // 定义处理
    @Around("fnc()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
//        TimeConsumeLog timeConsumeLog = method.getAnnotation(TimeConsumeLog.class);
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
//        String methodDesc = timeConsumeLog.methodDesc();
        logger.info("methodName: {} ==> cost {}ms", methodName, costTime);
        return result;
    }
}

