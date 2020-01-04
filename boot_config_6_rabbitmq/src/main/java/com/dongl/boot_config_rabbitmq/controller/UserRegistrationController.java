package com.dongl.boot_config_swagger.controller;

import com.dongl.boot_config_swagger.service.IntegralBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/31 23:47
 */
@RestController
@Slf4j
@Api(description = "用户注册控制类")
public class UserRegistrationController {

    @Autowired
    private IntegralBusinessService ibService;

    @GetMapping("/async")
    @ApiOperation(value="用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前登录的用户id", dataType = "String",paramType = "query") })
    public String registration(){
        log.info("新用户注册！");
        ibService.addIntegral();
        return "ok";
    }

    @ApiOperation(value="用户修改方法")
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
