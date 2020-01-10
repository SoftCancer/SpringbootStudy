package com.dongl.boot_config_8_exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling  // 定时注解
public class BootExceptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootExceptionApplication.class, args);
    }

}
