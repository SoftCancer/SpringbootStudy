package com.dongl.boot_config_6_rabbitmq_fanout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling  // 定时注解
public class BootRabbitMQFanoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootRabbitMQFanoutApplication.class, args);
    }

}
