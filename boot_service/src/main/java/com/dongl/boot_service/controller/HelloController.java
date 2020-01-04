package com.dongl.boot_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/30 17:49
 */
@RestController()
public class HelloController {
    @GetMapping("/hello")
    public String helloWord(){
        return "Hello SpringBoot";
    }
}
