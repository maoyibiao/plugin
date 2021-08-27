package com.wisdom.plugin.mbgp.configuration;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author maoyibiao
 * 自定义生成
 */
public abstract class AbstractInjectionConfiguration<T extends InjectionConfig> extends AbstractProcessConfiguration<T>{

    public T getInjectionConfig(GeneratorContext generatorContext) throws IllegalAccessException, InstantiationException {
        T obj = this.getInstance();
        obj.initMap();
        obj = this.config(generatorContext,obj);
        return this.injectionConfig(generatorContext,obj);
    }

    protected T getInstance() throws IllegalAccessException, InstantiationException {
        T obj = null;
        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type;
            if(p.getActualTypeArguments().length > 0) {
                Class c = (Class) p.getActualTypeArguments()[0];
                obj = (T) c.newInstance();
            }
        }
        if(obj == null){
            obj = (T) new InjectionConfig() {
                @Override
                public void initMap() {

                }
            };
        }
        return obj;
    }

    public abstract T injectionConfig(GeneratorContext context, T config);
}
