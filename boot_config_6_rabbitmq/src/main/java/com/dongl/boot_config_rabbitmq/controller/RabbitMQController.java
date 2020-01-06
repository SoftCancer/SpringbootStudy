package com.dongl.boot_config_rabbitmq.controller;

import com.dongl.boot_config_rabbitmq.common.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/6 12:34
 */
@RestController
public class RabbitMQController {

    @Autowired
    private Provider provider;

    @GetMapping("/provider")
    public void Runprovider() {
        try {

            while (true){
                Thread.sleep(2000);
                provider.provider();
                System.out.println("睡眠 1秒 ！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
