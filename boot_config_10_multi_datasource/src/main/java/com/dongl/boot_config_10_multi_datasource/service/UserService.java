package com.dongl.boot_config_10_multi_datasource.service;

import com.dongl.boot_config_10_multi_datasource.dao.master.UserMapper;
import com.dongl.boot_config_10_multi_datasource.entity.User;
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

    public void addUser(User user){
        userMapper.insertSelective(user);
    }

    public User getUserById(String id){
       return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByName(String name){
       return userMapper.getUserByName(name);
    }


}
