package com.dongl.boot_config_7_response_base.controller;

import com.dongl.boot_config_7_response_base.common.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/6 12:34
 */
@RestController
public class TopicController {

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
