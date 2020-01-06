package com.dongl.boot_config_6_rabbitmq_topic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling  // 定时注解
public class BootRabbitMQTopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootRabbitMQTopicApplication.class, args);
    }

}
