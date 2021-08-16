package com.wisdom.plugin.mbgp.config;

import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author maoyibiao
 * 统一赋值逻辑
 */
public abstract class AbstractProcessConfiguration<T> implements IGeneratorConfiguration<T>{
    public T config(GeneratorContext context,T config){
        Field[] fields = config.getClass().getDeclaredFields();
        for(Field field:fields) {
            String fieldName = field.getName();
            String className = config.getClass().getSimpleName();
            String key = "mbg." + className.substring(0,1).toLowerCase() + className.substring(1) + "." + fieldName;
            if(context.containsKey(key)) {
                Object value = context.get(key);
                ReflectionUtils.makeAccessible(field);
                if(field.getType().isAssignableFrom(Set.class)){
                    Set set = new HashSet();
                    set.addAll(Arrays.asList(value.toString().split(",")));
                    ReflectionUtils.setField(field,config,set);
                }else {
                    ReflectionUtils.setField(field, config, value);
                }

            }
        }
        return config;
    }
}
