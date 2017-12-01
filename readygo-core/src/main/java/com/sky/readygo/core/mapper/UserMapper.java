package com.sky.readygo.core.mapper;

import com.sky.readygo.core.domain.User;

import java.util.List;

/**
 * @Author: wurenqing
 * @Description:
 * @Date 2017-12-01 10:46
 */
public interface UserMapper {

    List<User> selectAll();

    int updateById(User user);
}
