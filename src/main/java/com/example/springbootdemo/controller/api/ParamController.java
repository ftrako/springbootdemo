package com.example.springbootdemo.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * 检验请求参数
 *
 * @author ftrako
 * @version 1.0 2022-10-26 15:06
 **/
@Validated
@RestController
@RequestMapping("/v1/param")
public class ParamController {
    private static final Logger logger = LogManager.getLogger(FileController.class);

    @GetMapping("/check")
    public String check(@NotEmpty(message = "不能为空") String name) {
        logger.debug("name=" + name);
        return "ok";
    }
}
