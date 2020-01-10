package com.dongl.boot_config_8_exception.controller;

import com.dongl.boot_config_8_exception.common.exception.CustomException;
import com.dongl.boot_config_8_exception.common.response.Result;
import com.dongl.boot_config_8_exception.entity.UserEntity;
import com.dongl.boot_config_8_exception.enums.ResultCodeEunm;
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


    @GetMapping("/getStr")
    public String getStr(){

        return "Hello SpringBoot !";
    }

    @GetMapping("/custom")
    public void custom() {
        throw new CustomException(ResultCodeEunm.USER_EXISTED);
    }
}
