package com.dongl.boot_config_async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/31 23:27
 */
@Service
@Slf4j
public class IntegralBusinessService {

    @Async
    public void addIntegral(){
        try {
            Thread.sleep(1000*5);
            log.info("新用户，赠送100积分！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("threadPoolTaskExecutorOne")
    public void addRedis(){
        try {
            Thread.sleep(1000*5);
            log.info("向Redis 中缓存数据！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("threadPoolTaskExecutorTwo")
    public void APPPush(){
        try {
            Thread.sleep(1000*5);
            log.info("向APP 中push 数据！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
