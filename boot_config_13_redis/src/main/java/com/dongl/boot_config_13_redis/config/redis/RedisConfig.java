package com.dongl.boot_config_13_redis.config.redis;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @description:  Redis 连接池及工厂配置类
 * @author: YaoGuangXun
 * @date: 2020/1/15 11:42
 */
@Configuration
//  注解是启用Spring应用程序上下文的自动配置
@EnableAutoConfiguration
public class RedisConfig {


    /**
     *  获取JedisPoolConfig配置
     * @Author YaoGuangXun
     * @Date 12:11 2020/1/15
     **/
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")   // 注解是用于读取配置文件的信息
    public JedisPoolConfig getRedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂 获取JedisConnectionFactory工厂
     * @Author YaoGuangXun
     * @Date 12:08 2020/1/15
     **/
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory() {
        //单机版jedis
        RedisStandaloneConfiguration redisStandaConfiguration = new RedisStandaloneConfiguration();
        //设置redis服务器的host或者ip地址
        redisStandaConfiguration.setHostName("192.168.1.107");
        //设置默认使用的数据库
        redisStandaConfiguration.setDatabase(0);
        //设置密码
        redisStandaConfiguration.setPassword(RedisPassword.of("123456"));
        //设置redis的服务的端口号
        redisStandaConfiguration.setPort(6379);
        //获得默认的连接池构造器(怎么设计的，为什么不抽象出单独类，供用户使用呢)
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        //指定jedisPoolConifig来修改默认的连接池构造器（真麻烦，滥用设计模式！）
        jpcb.poolConfig(getRedisPoolConfig());
        //通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        //单机配置 + 客户端配置 = jedis连接工厂
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaConfiguration, jedisClientConfiguration);
        return factory;
    }

    /**
     * 获取RedisTemplate模板
     * @Author YaoGuangXun
     * @Date 12:12 2020/1/15
     **/
    @Bean
    public RedisTemplate<?,?> getRedisTemplate(){
        JedisConnectionFactory factory = getConnectionFactory();
        RedisTemplate<?,?> template = new StringRedisTemplate(factory);
        return template;
    }
}
