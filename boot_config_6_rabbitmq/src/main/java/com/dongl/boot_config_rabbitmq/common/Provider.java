package com.dongl.boot_config_rabbitmq.common;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 消息提供者
 * @author: YaoGuangXun
 * @date: 2020/1/5 15:22
 */
@Component
public class Provider {

    //使用RabbitTemplate,这提供了接收/发送等等方法
    @Autowired
    RabbitTemplate rabbitTemplate;


    public String  provider(){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       // 向队列（dongl_rabbirmq）中提供（发送）消息
        rabbitTemplate.convertAndSend("dongl_rabbirmq","Hello RabbitMQ ："+ sdf.format(new Date()));
        return "OK";
    }

}
