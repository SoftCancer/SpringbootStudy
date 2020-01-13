package com.dongl.boot_config_11_druid.dao.master;

import com.dongl.boot_config_11_druid.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getUserByName(String name);
}