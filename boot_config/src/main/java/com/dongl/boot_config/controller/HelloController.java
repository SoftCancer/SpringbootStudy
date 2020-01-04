package com.dongl.boot_config.controller;

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
}
