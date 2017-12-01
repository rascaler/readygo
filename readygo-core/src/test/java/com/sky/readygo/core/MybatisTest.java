package com.sky.readygo.core;

import com.sky.readygo.core.domain.User;
import com.sky.readygo.core.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author: wurenqing
 * @Description:
 * @Date 2017-12-01 10:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-context.xml")
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMybatis() {
        List<User> users = userMapper.selectAll();
        return;
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(12);
        user.setNickName("qing");
        user.setName("qing");
        user.setEmail("rascaler@163.com");
        int result = userMapper.updateById(user);
        return;
    }



}

