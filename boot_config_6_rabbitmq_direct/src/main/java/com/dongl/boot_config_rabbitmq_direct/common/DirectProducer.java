package com.dongl.boot_config_rabbitmq_direct.common;

import com.dongl.boot_config_rabbitmq_direct.config.RabbitConfig;
import com.dongl.boot_config_rabbitmq_direct.entity.UserEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 生产者
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:39
 */
@Component
public class DirectProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息到交换机中
     * @Date 15:45 2020/1/6
     **/
    public void SenderOne(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);
        String msg = "生产者 ONE ： " + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_ONE,RabbitConfig.ROUTING_KEY_ONE,msg);
    }

    public void SenderTwo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);

        UserEntity user = new UserEntity("123","Albert",24);
        String msg = user.getName() +" 发送消息 " + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_TWO,RabbitConfig.ROUTING_KEY_TWO,msg);

    }

}
