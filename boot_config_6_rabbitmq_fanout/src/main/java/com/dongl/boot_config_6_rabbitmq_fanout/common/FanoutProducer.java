package com.dongl.boot_config_6_rabbitmq_fanout.common;

import com.dongl.boot_config_6_rabbitmq_fanout.config.RabbitConfig;
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
public class FanoutProducer {

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
        String msg = "I am fanout msg ======  :" + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE,"abcd.efg",msg);
    }

}
