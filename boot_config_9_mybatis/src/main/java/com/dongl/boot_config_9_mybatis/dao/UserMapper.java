package com.dongl.boot_config_9_mybatis.dao;

import com.dongl.boot_config_9_mybatis.entity.User;
import com.dongl.boot_config_9_mybatis.entity.UserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;


public interface UserMapper extends Mapper<User> {

}