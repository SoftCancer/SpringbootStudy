package com.dongl.boot_config_13_redis.dao.master;

import com.dongl.boot_config_13_redis.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getUserByName(String name);
}