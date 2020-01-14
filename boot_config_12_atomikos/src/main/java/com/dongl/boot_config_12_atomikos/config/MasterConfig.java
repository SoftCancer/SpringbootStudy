package com.dongl.boot_config_12_atomikos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 将application.properties配置文件中配置自动封装到实体类字段中
 * @author: YaoGuangXun
 * @date: 2020/1/14 1:27
 */

@Data
// 注意这个前缀要和application.properties文件的前缀一样
@ConfigurationProperties(prefix = "spring.datasource.druid.master")
public class MasterConfig {
    private String url;
    // 比如这个url在properties中是这样子的spring.datasource.druid.master.url
    private String username;
    private String password;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;
}
