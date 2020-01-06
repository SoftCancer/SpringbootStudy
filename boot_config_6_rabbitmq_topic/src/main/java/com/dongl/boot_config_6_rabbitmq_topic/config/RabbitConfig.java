package com.dongl.boot_config_6_rabbitmq_topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    // 队列routing key
    public static final String TOPIC_MESSAGE_KEY = "topic.message";
    public static final String TOPIC_MESSAGES_KEY = "topic.#";
    // 交换机exchange
    public static final String TOPIC_EXCHANGE = "topicExchange";

    //队列名称
    public static final String QUEUE_NAME_ONE = "topicOne";
    public static final String QUEUE_NAME_TWO = "topicTwo";
    /**
     * topic模式 Queue名为:topicOne 的队列
     */
    @Bean
    public Queue topicQueueOne(){
        return new Queue(QUEUE_NAME_ONE);
    }
    /**
     * topic模式 Queue名为:topicTwo s的队列
     */
    @Bean
    public Queue topicQueueTwo(){
        return new Queue(QUEUE_NAME_TWO);
    }

    /**
     * 设置交换机标识
     * @Date 15:27 2020/1/6
     **/
    @Bean
    public TopicExchange exchangeTopic(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeOne(Queue topicQueueOne,TopicExchange exchangeTopic){
        return BindingBuilder.bind(topicQueueOne).to(exchangeTopic).with(TOPIC_MESSAGE_KEY);
    }
    /**
     * 将队列 topic.& 与exchange绑定（&：任意字符串，均可匹配）， binding_key为topic.#,模糊匹配；
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeTwo(Queue topicQueueTwo,TopicExchange exchangeTopic){
        return BindingBuilder.bind(topicQueueTwo).to(exchangeTopic).with(TOPIC_MESSAGES_KEY);
    }



}
