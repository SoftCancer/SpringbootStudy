package com.dongl.boot_config_swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2019/12/31 23:33
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 自定义线程池
     * @return 
     * @Author YaoGuangXun
     * @Date 13:50 2020/1/1
     **/
    @Bean("threadPoolTaskExecutorOne")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutorOne(){
        // 1. 创建线程实例
        ThreadPoolTaskExecutor poolTaskExecutor= new ThreadPoolTaskExecutor();
        // 1. 线程池的核心数量（最小线程数）
        poolTaskExecutor.setCorePoolSize(10);
        // 1. 维护线程最大数量
        poolTaskExecutor.setMaxPoolSize(50);
        // 1.阻塞任务队列的大小
        poolTaskExecutor.setQueueCapacity(30);
        // 1.部分线程空闲最大存活时间，默认存活时间是60s
        poolTaskExecutor.setKeepAliveSeconds(150);
        // 1.线程名
        poolTaskExecutor.setThreadNamePrefix("Integral-1");
        // 1.表示线程池的饱和策略。
        // 如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。
        // 线程池提供了4种策略：
        //  AbortPolicy：直接抛出异常，这是默认策略；
        //  CallerRunsPolicy：用调用者所在的线程来执行任务；
        //  DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        //  DiscardPolicy：直接丢弃任务；
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 1.
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }

    /**
     * 自定义线程池
     * @return
     * @Author YaoGuangXun
     * @Date 13:50 2020/1/1
     **/
    @Bean("threadPoolTaskExecutorTwo")
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutorTwo(){
        // 1. 创建线程实例
        ThreadPoolTaskExecutor poolTaskExecutor= new ThreadPoolTaskExecutor();
        // 1. 线程池的核心数量（最小线程数）
        poolTaskExecutor.setCorePoolSize(15);
        // 1. 维护线程最大数量
        poolTaskExecutor.setMaxPoolSize(50);
        // 1.阻塞任务队列的大小
        poolTaskExecutor.setQueueCapacity(30);
        // 1.部分线程空闲最大存活时间，默认存活时间是60s
        poolTaskExecutor.setKeepAliveSeconds(150);
        // 1.线程名
        poolTaskExecutor.setThreadNamePrefix("Integral-2");
        // 1.表示线程池的饱和策略。
        // 如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。
        // 线程池提供了4种策略：
        //  AbortPolicy：直接抛出异常，这是默认策略；
        //  CallerRunsPolicy：用调用者所在的线程来执行任务；
        //  DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
        //  DiscardPolicy：直接丢弃任务；
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 1.
        poolTaskExecutor.initialize();
        return poolTaskExecutor;
    }


}
