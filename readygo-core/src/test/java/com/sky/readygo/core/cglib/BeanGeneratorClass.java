package com.sky.readygo.core.cglib;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

import java.util.Map;

/**
 * @Author: wurenqing
 * @Description:
 * @Date 2017-11-30 15:58
 */
public class BeanGeneratorClass {

    public static Class<?> createBeanClass(final String className,final Map<String, Class<?>> properties){

        final BeanGenerator beanGenerator = new BeanGenerator();

        beanGenerator.setNamingPolicy(new NamingPolicy(){
            @Override
            public String getClassName(final String prefix,final String source, final Object key, final Predicate names){
                return className;
            }});
        if(null != properties)
            BeanGenerator.addProperties(beanGenerator, properties);
        return (Class<?>) beanGenerator.createClass();
    }
}
