package com.sky.readygo.core.cglib;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wurenqing
 * @Description:
 * @Date 2017-11-30 15:15
 */
public class CglibTest {

    @Test
    public void generateClass() {
        final Map<String, Class<?>> properties =
                new HashMap<>();
        properties.put("id", Integer.class);
        properties.put("name", Integer.class);
        properties.put("createdDate", Date.class);
        properties.put("updatedDate", Date.class);
        properties.put("username", String.class);
        properties.put("password", String.class);
        properties.put("state", Integer.class);
        properties.put("nickName", String.class);
        properties.put("name", String.class);
        properties.put("email", String.class);
        properties.put("phone", String.class);
        properties.put("sex", Integer.class);
        properties.put("enterpriseId", Integer.class);



        final Class<?> beanClass =BeanGeneratorClass.createBeanClass("com.sky.readygo.core.domain.User", properties);
        System.out.println(beanClass);
        for(final Method method : beanClass.getDeclaredMethods()){
            System.out.println(method);
        }
    }

}
