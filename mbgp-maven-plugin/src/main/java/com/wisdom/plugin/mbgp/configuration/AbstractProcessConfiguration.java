package com.wisdom.plugin.mbgp.configuration;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author maoyibiao
 * 统一赋值逻辑
 */
public abstract class AbstractProcessConfiguration<T> implements IGeneratorConfiguration<T>{
    public T config(GeneratorContext context,T config){
        Field[] fields = config.getClass().getDeclaredFields();
        String className = this.getClassName(config);
        for(Field field:fields) {
            String fieldName = field.getName();
            String key = "mbgp." + className + "." + fieldName;
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

    protected String getClassName(T config){
        String className = null;
        if(config instanceof DataSourceConfig){
            className = DataSourceConfig.class.getSimpleName();
        }
        if(config instanceof GlobalConfig){
            className = GlobalConfig.class.getSimpleName();
        }
        if(config instanceof InjectionConfig){
            className = InjectionConfig.class.getSimpleName();
        }
        if(config instanceof PackageConfig){
            className = PackageConfig.class.getSimpleName();
        }
        if(config instanceof StrategyConfig){
            className = StrategyConfig.class.getSimpleName();
        }
        if(config instanceof TemplateConfig){
            className = TemplateConfig.class.getSimpleName();
        }
        return className.substring(0,1).toLowerCase() + className.substring(1);
    }
}
