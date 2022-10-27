package com.example.springbootdemo.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 * @author chendajian
 * @version 1.0 2022-10-26 17:25
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionsAnnotation {
}
