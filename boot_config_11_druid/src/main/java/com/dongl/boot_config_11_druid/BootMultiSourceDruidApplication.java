package com.dongl.boot_config_11_druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

//@MapperScan("com.dongl.boot_config_10_test.dao")
@SpringBootApplication
public class BootMultiSourceDruidApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BootMultiSourceDruidApplication.class, args);
    }

    /**
     * 用于打印 所使用的数据连接池
     * @Author YaoGuangXun
     * @Date 21:23 2020/1/13
     **/
    @Autowired
    DataSource dataSource;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("DATASOURCE = " + dataSource);
    }

}
