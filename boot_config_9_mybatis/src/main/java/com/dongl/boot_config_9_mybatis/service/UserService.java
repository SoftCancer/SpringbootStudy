package com.dongl.boot_config_9_mybatis.service;

import com.dongl.boot_config_9_mybatis.dao.UserMapper;
import com.dongl.boot_config_9_mybatis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

    public void addUser(User user){
        userMapper.insertSelective(user);
    }

    public User getUserById(String id){
       return userMapper.selectByPrimaryKey(id);
    }


}
