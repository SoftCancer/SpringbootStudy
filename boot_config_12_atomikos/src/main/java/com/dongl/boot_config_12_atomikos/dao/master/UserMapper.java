package com.dongl.boot_config_12_atomikos.dao.master;

import com.dongl.boot_config_12_atomikos.entity.User;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getUserByName(String name);
}