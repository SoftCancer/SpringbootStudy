package com.dongl.boot_config_6_rabbitmq_topic.controller;

import com.dongl.boot_config_6_rabbitmq_topic.common.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/6 12:34
 */
@RestController
public class DirectController {

    @Autowired
    private TopicProducer topicProducer;

    @GetMapping("/senderOne")
    public void senderOne() {
        topicProducer.SenderOne();
    }

    @GetMapping("/senderTwo")
    public void senderTwo() {
        topicProducer.SenderTwo();
    }



}
