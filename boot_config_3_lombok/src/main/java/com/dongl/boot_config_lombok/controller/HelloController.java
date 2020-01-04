package com.dongl.boot_config_lombok.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/30 17:49
 */
@RestController()
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${boot.msg}")
    private String msg;

    @GetMapping("/msg")
    public String getMsg(){
        return msg;
    }

    @GetMapping("/hello")
    public String helloWord(){
        return "Hello SpringBoot";
    }

    @GetMapping("/log")
    public void log(){
        try {
            logger.trace("********** trace*********");
            logger.debug("********** debug*********");
            logger.info("********** info*********");
            logger.warn("********** warn*********");
            logger.error("********** error*********");
            int i = 0/9;

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
