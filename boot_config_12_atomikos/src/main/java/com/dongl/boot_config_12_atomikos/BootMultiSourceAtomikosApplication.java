package com.dongl.boot_config_12_atomikos;

import com.dongl.boot_config_12_atomikos.config.MasterConfig;
import com.dongl.boot_config_12_atomikos.config.SlaveConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.sql.DataSource;



@EnableConfigurationProperties(value = {MasterConfig.class,SlaveConfig.class})
@SpringBootApplication
public class BootMultiSourceAtomikosApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BootMultiSourceAtomikosApplication.class, args);
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
