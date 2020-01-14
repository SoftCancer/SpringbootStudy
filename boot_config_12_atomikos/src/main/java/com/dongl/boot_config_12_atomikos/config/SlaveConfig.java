package com.dongl.boot_config_12_atomikos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/14 1:53
 */
@Data
@ConfigurationProperties(value = "spring.datasource.druid.slave")
public class SlaveConfig {
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
