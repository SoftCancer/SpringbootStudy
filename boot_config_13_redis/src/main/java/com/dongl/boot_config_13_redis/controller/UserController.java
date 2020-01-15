package com.dongl.boot_config_13_redis.controller;

import com.dongl.boot_config_13_redis.entity.User;
import com.dongl.boot_config_13_redis.service.PayService;
import com.dongl.boot_config_13_redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/11 21:46
 */
@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PayService payService;

    @GetMapping("/add")
    public String addUser() {
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            String uuid = UUID.randomUUID().toString();
//            uuid = uuid.replace("-", "");
//            user.setId(uuid);
//            user.setUsername("Albert" + String.valueOf(i));
//            user.setPassword("123456");
//            Random random = new Random();
//            int ran = random.nextInt(2);
//            user.setSex((byte)ran);
//            userService.addUser(user);
//        }

        return "ok";
    }

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable String id){
           return userService.getUserById(id);
    }

    @RequestMapping(value = "/name/{name}",method = RequestMethod.GET)
    public User getUserByName(@PathVariable String name){
           return userService.getUserByName(name);
    }


    @RequestMapping(value = "/pay",method = RequestMethod.GET)
    public String pay(){
        payService.pay("38be0128e4f747eea0bd88c2ad37d0f7","57b49d1f64f94aca9b634d3d70f797a8",20);
        return "Success Save！";
    }

}
