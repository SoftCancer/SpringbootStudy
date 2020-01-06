package com.dongl.boot_config_rabbitmq_direct.controller;

import com.dongl.boot_config_rabbitmq_direct.common.DirectProducer;
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
    private DirectProducer directProducer;

    @GetMapping("/senderOne")
    public void senderOne() {
        directProducer.SenderOne();
    }

    @GetMapping("/senderTwo")
    public void senderTwo() {
        directProducer.SenderTwo();
    }


}
