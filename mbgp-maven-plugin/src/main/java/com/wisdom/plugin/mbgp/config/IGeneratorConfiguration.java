package com.wisdom.plugin.mbgp.config;

import com.wisdom.plugin.mbgp.context.GeneratorContext;

public interface IGeneratorConfiguration<T> {
    public T config(GeneratorContext generatorContext, T config);
}
