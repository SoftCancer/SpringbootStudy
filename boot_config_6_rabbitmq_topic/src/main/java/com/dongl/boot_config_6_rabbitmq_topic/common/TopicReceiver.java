package com.dongl.boot_config_6_rabbitmq_topic.common;

import com.dongl.boot_config_6_rabbitmq_topic.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:53
 */

@Component
public class TopicReceiver {

    // 监听器监听指定的queue ,queues:可以直接写 或 定义成常量 或 配置在配置文件中。
    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_ONE)
    public void consumeOne(String msg){
        System.out.println("Receiver Queue TopicOne 的信息： " + msg);
    }

    // 监听器监听指定的queue
    @RabbitListener(queues = {"topicTwo"})
    public void consumeTwo(String msg){
        System.out.println("Receiver Queue TopicTwo 的信息： " + msg);
    }
}
