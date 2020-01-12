package com.dongl.boot_config_10_multi_datasource.dao.master;

import com.dongl.boot_config_10_multi_datasource.entity.User;
import com.dongl.boot_config_10_multi_datasource.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    User getUserByName(String name);
}