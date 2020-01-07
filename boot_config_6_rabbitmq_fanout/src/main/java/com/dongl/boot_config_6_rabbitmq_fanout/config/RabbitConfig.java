package com.dongl.boot_config_6_rabbitmq_fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

    // 队列 routing key
    public static final String QUEUE_NAME_A = "fanout.a";
    public static final String QUEUE_NAME_B = "fanout.b";
    public static final String QUEUE_NAME_C = "fanout.c";

    // 交换机exchange
    public static final String FANOUT_EXCHANGE = "fanout_Exchange";

    /**
     * fanout模式 Queue名为:fanout.a 的队列
     */
    @Bean
    public Queue fanoutQueueOne(){
        return new Queue(QUEUE_NAME_A);
    }
    
    /**
     * fanout模式 Queue名为:fanout.b 的队列
     */
    @Bean
    public Queue fanoutQueueTwo(){
        return new Queue(QUEUE_NAME_B);
    }

    /**
     * fanout模式 Queue名为:fanout.c 的队列
     */
    @Bean
    public Queue fanoutQueueThree(){
        return new Queue(QUEUE_NAME_C);
    }

    /**
     * 设置交换机标识
     * @Date 15:27 2020/1/6
     **/
    @Bean
    public FanoutExchange exchangefanout(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 将队列fanout.a与exchange绑定，
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeOne(Queue fanoutQueueOne,FanoutExchange exchangefanout){
        return BindingBuilder.bind(fanoutQueueOne).to(exchangefanout);
    }

    /**
     *
     * @Date 15:27 2020/1/6
     **/
    @Bean
    Binding bindingExchangeTwo(Queue fanoutQueueTwo,FanoutExchange exchangefanout){
        return BindingBuilder.bind(fanoutQueueTwo).to(exchangefanout);
    }

    /**
     *
     * @Date 15:27 2020/1/7
     **/
    @Bean
    Binding bindingExchangeThree(Queue fanoutQueueThree,FanoutExchange exchangefanout){
        return BindingBuilder.bind(fanoutQueueThree).to(exchangefanout);
    }



}
