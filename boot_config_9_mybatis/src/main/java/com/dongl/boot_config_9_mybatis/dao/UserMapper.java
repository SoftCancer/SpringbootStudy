package com.dongl.boot_config_9_mybatis.dao;

import com.dongl.boot_config_9_mybatis.entity.User;
import tk.mybatis.mapper.common.Mapper;


public interface UserMapper extends Mapper<User> {

    User getUserByName(String userName);

}