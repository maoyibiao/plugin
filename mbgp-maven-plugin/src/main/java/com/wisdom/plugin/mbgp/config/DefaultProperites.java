package com.wisdom.plugin.mbgp.config;

import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.HashMap;

/**
 * @author maoyibiao
 * 内置默认配置
 */
public class DefaultProperites extends HashMap<String,Object> {
    private final static Object[][] defaultParams = {
            {"mbgp.globalConfig.author","MybatisPlus Generator"},
            {"mbgp.globalConfig.open",false},
            {"mbgp.globalConfig.outputDir",System.getProperty("user.dir") + "/src/main/java"},
            {"mbgp.globalConfig.swagger2",true},
            {"mbgp.packageConfig.parent",""},
            {"mbgp.templateConfig.controller",""},
            {"mbgp.templateConfig.service",""},
            {"mbgp.templateConfig.serviceImpl",""},
            {"mbgp.templateConfig.xml",""},
            {"mbgp.strategyConfig.naming", NamingStrategy.underline_to_camel},
            {"mbgp.strategyConfig.columnNaming",NamingStrategy.underline_to_camel},
            {"mbgp.strategyConfig.entityLombokModel",true},
            {"mbgp.strategyConfig.restControllerStyle",false},
            {"mbgp.strategyConfig.enableSqlFilter",false},
            {"mbgp.strategyConfig.include",".*"},
            {"mbgp.strategyConfig.setTablePrefix",true},
            {"mbgp.strategyConfig.restControllerStyle",false},
            {"mbgp.strategyConfig.entityTableFieldAnnotationEnable",true},
            //simple 生成一个 && off 不生成 && 其他值生成全部
            {"mbgp.testConfig.isTest","simple"}
    };
    {
        for(Object[]  defaultParam: defaultParams){
            this.put(defaultParam[0].toString(),defaultParam[1]);
        }
    }
}
