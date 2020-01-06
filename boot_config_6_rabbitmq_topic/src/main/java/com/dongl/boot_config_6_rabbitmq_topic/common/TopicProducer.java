package com.dongl.boot_config_6_rabbitmq_topic.common;

import com.dongl.boot_config_6_rabbitmq_topic.config.RabbitConfig;
import com.dongl.boot_config_6_rabbitmq_topic.entity.UserEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 生产者
 * @author: YaoGuangXun
 * @date: 2020/1/6 15:39
 */
@Component
public class TopicProducer {

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
        String msg = "I am topic.mesaage msg ======  :" + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE,"topic.message",msg);
    }

    public void SenderTwo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);

        String msg = "I am topic.** msg ######## : " + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE,"topic.**",msg);
    }

    /**
     * @Param  通过定时任务执行
     * @Date 22:40 2020/1/6
     **/
    @Scheduled(cron="0/5 * * * * ? ")   //每5秒执行一次
    public void send() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String dateStr = sdf.format(date);

        String msg = "I am topic.mesaages msg ######## : " + dateStr ;
        System.err.println(msg);
        // 向指定的交换机发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE,RabbitConfig.TOPIC_MESSAGES_KEY,msg);
    }
}
