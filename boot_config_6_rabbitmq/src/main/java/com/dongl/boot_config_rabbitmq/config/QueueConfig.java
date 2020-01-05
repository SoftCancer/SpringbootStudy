package com.dongl.boot_config_rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 配置队列实例
 * @author: YaoGuangXun
 * @date: 2020/1/5 15:23
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue queue(){
        return new Queue("dongl_rabbirmq");
    }
}
