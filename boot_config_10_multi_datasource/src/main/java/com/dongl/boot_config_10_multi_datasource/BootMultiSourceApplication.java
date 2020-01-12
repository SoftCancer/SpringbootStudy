package com.dongl.boot_config_10_multi_datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

//@MapperScan("com.dongl.boot_config_10_multi_datasource.dao")
@SpringBootApplication
public class BootMultiSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMultiSourceApplication.class, args);
    }
}
