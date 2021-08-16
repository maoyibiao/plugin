package com.wisdom.plugin.mbgp.config;

import com.wisdom.plugin.mbgp.context.GeneratorContext;
/**
 * @author maoyibiao
 * 配置接口类
 */
public interface IGeneratorConfiguration<T> {
    public T config(GeneratorContext generatorContext, T config);
}
