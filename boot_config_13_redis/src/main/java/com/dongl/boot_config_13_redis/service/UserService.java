package com.dongl.boot_config_13_redis.service;

import com.alibaba.fastjson.JSON;
import com.dongl.boot_config_13_redis.common.redis.IRedisService;
import com.dongl.boot_config_13_redis.dao.master.UserMapper;
import com.dongl.boot_config_13_redis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: YaoGuangXun
 * @date: 2020/1/11 21:42
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRedisService redisService;


    public void addUser(User user){
        userMapper.insertSelective(user);
    }

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

    public User getUserByName(String name){

       return userMapper.getUserByName(name);
    }
}
