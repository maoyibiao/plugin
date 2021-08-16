package com.wisdom.plugin.mbgp.config;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;

/**
 * @author maoyibiao
 * 内置默认配置
 */
public class DefaultProperites extends HashMap<String,Object> {
    private final static Object[][] defaultParams = {
            {"mbg.globalConfig.author","MybatisPlus Generator"},
            {"mbg.globalConfig.open",false},
            {"mbg.globalConfig.outputDir",System.getProperty("user.dir") + "/src/main/java"},
            {"mbg.globalConfig.swagger2",true},
            {"mbg.templateConfig.controller",""},
            {"mbg.templateConfig.service",""},
            {"mbg.templateConfig.serviceImpl",""},
            {"mbg.templateConfig.xml",""},
            {"mbg.strategyConfig.naming", NamingStrategy.underline_to_camel},
            {"mbg.strategyConfig.columnNaming",NamingStrategy.underline_to_camel},
            {"mbg.strategyConfig.entityLombokModel",true},
            {"mbg.strategyConfig.restControllerStyle",false},
            {"mbg.strategyConfig.enableSqlFilter",false},
            {"mbg.strategyConfig.include",".*"},
            {"mbg.strategyConfig.setTablePrefix",true},
            {"mbg.strategyConfig.restControllerStyle",false},
    };
    {
        for(Object[]  defaultParam: defaultParams){
            this.put(defaultParam[0].toString(),defaultParam[1]);
        }
    }
}
