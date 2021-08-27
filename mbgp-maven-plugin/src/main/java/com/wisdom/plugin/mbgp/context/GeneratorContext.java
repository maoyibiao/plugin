package com.wisdom.plugin.mbgp.context;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.wisdom.plugin.mbgp.config.DefaultProperites;
import com.wisdom.plugin.mbgp.configuration.*;
import com.wisdom.plugin.mbgp.template.DefaultFreemarkerTemplateEngine;
import com.wisdom.plugin.utils.PluginConfig;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class GeneratorContext extends HashMap<String,Object> {
    private Logger logger = Logger.getLogger(GeneratorContext.class.getName());
    private DefaultProperites defaultProperites = new DefaultProperites();
    private GlobalConfiguration globalConfiguration;
    private DataSourceConfiguration dataSourceConfiguration;
    private List<AbstractInjectionConfiguration> injectionConfigurations;
    private PackageConfiguration packageConfiguration;
    private StrategyConfiguration strategyConfiguration;
    private TemplateConfiguartion templateConfiguartion;
    private AutoGenerator mpg = new AutoGenerator();
    private GlobalConfig globalConfig = new GlobalConfig();
    private DataSourceConfig dataSourceConfig = new DataSourceConfig();
    private PackageConfig packageConfig = new PackageConfig();
    private TemplateConfig templateConfig = new TemplateConfig();
    private StrategyConfig strategyConfig = new StrategyConfig();

    private GeneratorContext(PluginConfig pc){
        this.putAll(defaultProperites);
        this.putAll(pc);
        logger.info(this.toString());
    }


    public GeneratorContext(GlobalConfiguration gc,DataSourceConfiguration dc,List<AbstractInjectionConfiguration> ics,
                            PackageConfiguration pc,StrategyConfiguration sc,TemplateConfiguartion tc,PluginConfig p){
        this(p);
        this.globalConfiguration = gc;
        this.dataSourceConfiguration = dc;
        this.injectionConfigurations = ics;
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
        this.strategyConfig = this.strategyConfiguration.config(this,strategyConfig);
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new DefaultFreemarkerTemplateEngine(this)).execute();

    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public List<AbstractInjectionConfiguration> getInjectionConfigurations() {
        return injectionConfigurations;
    }
}
