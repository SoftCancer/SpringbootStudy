package com.dongl.boot_config_7_response_base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling  // 定时注解
public class BootResponseBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootResponseBaseApplication.class, args);
    }

}
