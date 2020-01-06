package com.dongl.boot_config_rabbitmq_direct.common;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:53
 */

@Component
public class DirectReceiver {

    // 监听队列中的消息
    @RabbitListener(queues = {"DirectOne"})
    public void consumeOne(String msg){
        System.out.println("Receiver Queue DirectOne 的信息： " + msg);
    }

    // 监听队列中的消息
    @RabbitListener(queues = {"DirectTwo"})
    public void consumeTwo(String msg){
        System.out.println("Receiver Queue DirectTwo 的信息： " + msg);
    }
}
