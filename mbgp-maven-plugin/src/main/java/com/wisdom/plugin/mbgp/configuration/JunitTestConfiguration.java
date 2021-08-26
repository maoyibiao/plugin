package com.wisdom.plugin.mbgp.configuration;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.apache.commons.lang3.StringUtils;

import javax.naming.ConfigurationException;
import java.util.logging.Logger;

public class JunitTestConfiguration extends InjectionConfiguration {
    private boolean flag = true;
    private String templatePath = "/templates/MybatisGeneratorTest.java.ftl";
    private Logger logger = Logger.getLogger(JunitTestConfiguration.class.getName());
    @Override
    public InjectionConfig config(GeneratorContext generatorContext, InjectionConfig config) {
        InjectionConfig ic = super.config(generatorContext, config);
        String isTest = generatorContext.get("mbgp.testConfig.isTest").toString();
        if("off".equals(isTest.toLowerCase())){
            flag = false;
        }
        if(flag){
            ic.getFileOutConfigList().add(new FileOutConfig(templatePath) {
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
                            logger.throwing(JunitTestConfiguration.class.getName(), "config", new ConfigurationException("单元测试存放路径未配置"));
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
        return ic;
    }
}
