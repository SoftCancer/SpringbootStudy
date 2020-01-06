package com.dongl.boot_config_rabbitmq_direct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: RabbitMQ 的配置类
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:17
 */
@Configuration
public class RabbitConfig {

    /**
     * 如果多个生产者共用同一个交换机和路由关键字，
     * 则每个消费者都将收到消息
     * @Date 18:19 2020/1/6
     **/
    // 消息交换机名字
    public static final String DIRECT_EXCHANGE_ONE = "directExchangeOne";
    public static final String DIRECT_EXCHANGE_TWO = "directExchangeTwo";

    // 路由关键字
    public static final String ROUTING_KEY_ONE = "direct_key_one";
    public static final String ROUTING_KEY_TWO = "direct_key_two";

    /**
     * Direct模式 Queue名为:DirectOne 的队列
     */
    @Bean
    public Queue directQueueOne(){
        return new Queue("DirectOne");
    }
    /**
     * Direct模式 Queue名为:DirectTwo s的队列
     */
    @Bean
    public Queue directQueueTwo(){
        return new Queue("DirectTwo");
    }

    /**
     * 交换机
     * @Date 15:27 2020/1/6
     **/
    @Bean
    DirectExchange exchangeOne(){
        return new DirectExchange(DIRECT_EXCHANGE_ONE);
    }
    /**
     * 交换机
     * @Date 15:27 2020/1/6
     **/
    @Bean
    DirectExchange exchangeTwo(){
        return new DirectExchange(DIRECT_EXCHANGE_TWO);
    }

    /**
     * 将DirectOne 消息队列绑定到directExchange交换机上, bingding-key 为direct_key
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeOne(Queue directQueueOne,DirectExchange exchangeOne){
        return BindingBuilder.bind(directQueueOne).to(exchangeOne).with(DIRECT_EXCHANGE_ONE);
    }
    /**
     * 将DirectOne 消息队列绑定到directExchange交换机上, bingding-key 为direct_key
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeTwo(Queue directQueueTwo,DirectExchange exchangeTwo){
        return BindingBuilder.bind(directQueueTwo).to(exchangeTwo).with(ROUTING_KEY_TWO);
    }



}
