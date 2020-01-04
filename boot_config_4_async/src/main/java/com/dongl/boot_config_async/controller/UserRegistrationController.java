package com.dongl.boot_config_async.controller;

import com.dongl.boot_config_async.service.IntegralBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/31 23:47
 */
@RestController
@Slf4j
public class UserRegistrationController {

    @Autowired
    private IntegralBusinessService ibService;

    @GetMapping("/async")
    public String registration(){
        log.info("新用户注册！");
        ibService.addIntegral();
        return "ok";
    }

    @GetMapping("/upUser")
    public String updateUser(){
        log.info("用户修改！");
        ibService.addRedis();
        return "ok";
    }

    @GetMapping("/order")
    public String placeOrder(){
        log.info("用户下订单！");
        ibService.APPPush();
        return "ok";
    }



}
