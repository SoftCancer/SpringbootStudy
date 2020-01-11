package com.dongl.boot_config_9_mybatis.controller;

import com.dongl.boot_config_9_mybatis.entity.User;
import com.dongl.boot_config_9_mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/11 21:46
 */
@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String addUser() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "");
            user.setId(uuid);
            user.setUsername("Albert" + String.valueOf(i));
            user.setPassword("123456");
            Random random = new Random();
            int ran = random.nextInt(2);
            user.setSex((byte)ran);
            userService.addUser(user);
        }

        return "ok";
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable String id){
           return userService.getUserById(id);
    }

}
