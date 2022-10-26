package com.example.springbootdemo.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 *
 * @author ftrako
 * @version 1.0 2022-10-26 15:27
 **/
@RestController
@RequestMapping("/v1/log")
public class IndexController {
    private static final Logger logger = LogManager.getLogger(IndexController.class);

    @Value("${server.port}")
    private String port;

    @RequestMapping("/log4j2")
    public String index() {
        logger.trace("Trace log");
        logger.debug("Debug log");
        logger.info("Info log");
        logger.warn("Hey, This is a warning!");
        logger.error("Oops! We have an Error. OK");
        logger.fatal("Damn! Fatal error. Please fix me.");
        return "hello, my springboot, port:" + port;
    }
}
