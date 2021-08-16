package com.wisdom.plugin.mbgp.context;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wisdom.plugin.mbgp.config.*;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class GeneratorContext extends HashMap<String,Object> {
    private DefaultProperites defaultProperites = new DefaultProperites();
    private GlobalConfiguration globalConfiguration;
    private DataSourceConfiguration dataSourceConfiguration;
    private InjectionConfiguration injectionConfiguration;
    private PackageConfiguration packageConfiguration;
    private StrategyConfiguration strategyConfiguration;
    private TemplateConfiguartion templateConfiguartion;
    private AutoGenerator mpg = new AutoGenerator();
    private GlobalConfig globalConfig = new GlobalConfig();
    private DataSourceConfig dataSourceConfig = new DataSourceConfig();
    private PackageConfig packageConfig = new PackageConfig();
    private TemplateConfig templateConfig = new TemplateConfig();
    private InjectionConfig injectionConfig = new InjectionConfig() {
        @Override
        public void initMap() {

        }
    };
    private StrategyConfig strategyConfig = new StrategyConfig();

    private GeneratorContext(Properties properties){
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement().toString();
            String value = properties.get(key).toString();
            this.put(key,value);
        }
        this.putAll(defaultProperites);
    }

    public GeneratorContext(GlobalConfiguration gc,DataSourceConfiguration dc,InjectionConfiguration ic,
                            PackageConfiguration pc,StrategyConfiguration sc,TemplateConfiguartion tc,Properties properties){
        this(properties);
        this.globalConfiguration = gc;
        this.dataSourceConfiguration = dc;
        this.injectionConfiguration = ic;
        this.packageConfiguration = pc;
        this.strategyConfiguration = sc;
        this.templateConfiguartion = tc;

    }

    public void exec(){
        this.globalConfig = this.globalConfiguration.config(this,globalConfig);
        mpg.setGlobalConfig(globalConfig);
        this.dataSourceConfig = this.dataSourceConfiguration.config(this,dataSourceConfig);
        mpg.setDataSource(dataSourceConfig);
        this.packageConfig = this.packageConfiguration.config(this,packageConfig);
        mpg.setPackageInfo(packageConfig);
        this.templateConfig = this.templateConfiguartion.config(this,templateConfig);
        mpg.setTemplate(templateConfig);
        this.injectionConfig = this.injectionConfiguration.config(this,injectionConfig);
        mpg.setCfg(injectionConfig);
        this.strategyConfig = this.strategyConfiguration.config(this,strategyConfig);
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine()).execute();

    }

}
