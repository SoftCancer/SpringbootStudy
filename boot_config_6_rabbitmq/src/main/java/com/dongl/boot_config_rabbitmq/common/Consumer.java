package com.dongl.boot_config_rabbitmq.common;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: YaoGuangXun
 * @date: 2020/1/5 16:02
 */
@Component
public class Consumer {

    // 监听队列消息
    @RabbitListener(queues = "dongl_rabbirmq")
    public void consumer(String msg){
        System.out.println("consumer : " + msg);
    }
}
