package com.dongl.boot_config_7_response_base.controller;

import com.dongl.boot_config_7_response_base.common.Result;
import com.dongl.boot_config_7_response_base.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/10 15:43
 */
@RestController
public class UserController {


    @GetMapping("/user")
    public Result getUser(){
        UserEntity user = new UserEntity();
        user.setAge(23);
        user.setId("123");
        user.setName("Albert");
        return Result.success(user);
    }

    @GetMapping("/users")
    public UserEntity getUsers(){
        UserEntity user = new UserEntity();
        user.setAge(33);
        user.setId("1234");
        user.setName("Albert");
        return user;
    }

    @GetMapping("/getStr")
    public String getStr(){

        return "Hello SpringBoot !";
    }
}
