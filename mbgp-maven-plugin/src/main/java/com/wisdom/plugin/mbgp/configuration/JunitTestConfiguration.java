package com.wisdom.plugin.mbgp.configuration;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.wisdom.plugin.mbgp.config.TestConfig;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.apache.commons.lang3.StringUtils;

import javax.naming.ConfigurationException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class JunitTestConfiguration extends AbstractInjectionConfiguration<TestConfig> {
    private boolean flag = true;
    private String templatePath = "/templates/MybatisGeneratorTest.java.ftl";
    private Logger logger = Logger.getLogger(JunitTestConfiguration.class.getName());
    @Override
    public TestConfig injectionConfig(GeneratorContext generatorContext, TestConfig config) {
        String isTest = config.getIsTest();
        if("off".equals(isTest.toLowerCase())){
            flag = false;
        }
        if(flag){
            if(config.getFileOutConfigList() == null){
                config.setFileOutConfigList(new ArrayList<>());
            }
            config.getMap().put("testConfig",config);
            config.getFileOutConfigList().add(new FileOutConfig(templatePath) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    if(flag) {
                        String mapperLocation = null;
                        String parent = generatorContext.get("mbgp.packageConfig.parent").toString();
                        if (StringUtils.isNotBlank(parent)) {
                            mapperLocation = parent + ".mapper";
                        }
                        if (generatorContext.containsKey("mbgp.packageConfig.entity")) {
                            mapperLocation = generatorContext.get("mbgp.packageConfig.mapper").toString();
                        }
                        if (mapperLocation == null) {
                            logger.throwing(JunitTestConfiguration.class.getName(), "config", new ConfigurationException("?????????????????????????????????"));
                        }
                        if("simple".equals(isTest.toLowerCase())){
                            flag = false;
                        }
                        return System.getProperty("user.dir") + "/src/test/java/" + mapperLocation.replaceAll("\\.", "/") + "/" + tableInfo.getMapperName() + "Test" + StringPool.DOT_JAVA;
                    }
                    return null;
                }
            });
        }
        return config;
    }
}
