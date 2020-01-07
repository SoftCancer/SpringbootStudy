package com.dongl.boot_config_6_rabbitmq_fanout.controller;

import com.dongl.boot_config_6_rabbitmq_fanout.common.FanoutProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/6 12:34
 */
@RestController
public class FanoutController {

    @Autowired
    private FanoutProducer topicProducer;

    @GetMapping("/senderOne")
    public void senderOne() {
        topicProducer.SenderOne();
    }

}
