## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 在boot_config_11_multi_datasource 的基础上 解决分布式事务问题
### 步骤1： 在 pom.xml 中添加依赖
```aidl
    <!--添加redis缓存依赖-->
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
        <version>1.5.0.RELEASE</version>
    </dependency>
```
### 步骤2： 配置application.properties 文件
```aidl
# Redis 配置
# Redis数据库索引（默认为0）,如果设置为1，那么存入的key-value都存放在select 1中
spring.redis.database=0 
# Redis服务器地址
spring.redis.host=192.168.1.107
# Redis服务器连接端口
spring.redis.port=6379 
# Redis服务器连接密码（默认为空）
spring.redis.password=123456
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.jedis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.jedis.pool.max-wait=-1ms
# 连接池中的最大空闲连接
spring.redis.jedis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.jedis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.jedis.timeout= 2000ms
```

###步骤3：Redis 连接池配置
```aidl
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

```

### 步骤4：Redis常用方法封装

1.Redis常用方法 接口
```aidl
package com.dongl.boot_config_13_redis.common.redis;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/15 12:14
 */
public interface IRedisService {
    /**
     * set存数据
     * @param key
     * @param value
     * @return
     */
    boolean set(String key, String value);

    /**
     * get获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置有效天数
     * @param key
     * @param expire
     * @return
     */
    boolean expire(String key, long expire);

    /**
     * 移除数据
     * @param key
     * @return
     */
    boolean remove(String key);

}

```

2.Redis常用方法 接口实现
```aidl
package com.dongl.boot_config_13_redis.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:  Redis常用方法封装
 * @author: YaoGuangXun
 * @date: 2020/1/15 12:14
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService {

    @Autowired
    private RedisTemplate<String,?> redisTemplate;

    @Override
    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key),serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    @Override
    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean remove(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(key.getBytes());
                return true;
            }
        });
        return result;
    }
}

```

###步骤5：演示
演示代码 Service 层：
```aidl
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRedisService redisService;

    public User getUserById(String id){
        // 从缓存中获取
        String value = redisService.get(id);
        log.info("key :{} -- value:{} ",id ,value);
        if (null == value){
            User user = userMapper.selectByPrimaryKey(id);
            log.info("user:{}",user);
            redisService.set(id, JSON.toJSONString(user));
            return user;
        }
        // 把 从Redis 缓存中获取的JSON，序列化为User 对象
        User user = JSON.parseObject(value,User.class);
        log.info("直接从缓存中获取 : user:{}",user);
        return user;
    }
```

控制层代码：
```aidl

    @RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable String id){
           return userService.getUserById(id);
    }

```

访问：http://localhost:9090/get/38be0128e4f747eea0bd88c2ad37d0f7
从日志中可以看出,并没有查询数据库
```aidl
2020-01-15 17:45:57.338  INFO 3388 --- [nio-9090-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-01-15 17:45:57.419  INFO 3388 --- [nio-9090-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 81 ms
2020-01-15 17:45:57.945  INFO 3388 --- [nio-9090-exec-1] c.d.b.service.UserService                : key :38be0128e4f747eea0bd88c2ad37d0f7 -- value:{"createTime":1578728358000,"deleted":0,"id":"38be0128e4f747eea0bd88c2ad37d0f7","password":"123456","sex":0,"updateTime":1578728358000,"username":"Albert3"} 
2020-01-15 17:45:58.184  INFO 3388 --- [nio-9090-exec-1] c.d.b.service.UserService                : 直接从缓存中获取 : user:com.dongl.boot_config_13_redis.entity.User@7a5c28f9
```

