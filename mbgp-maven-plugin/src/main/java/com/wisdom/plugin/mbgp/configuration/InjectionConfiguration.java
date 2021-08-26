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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author maoyibiao
 * 自定义mapper生成
 */
public class InjectionConfiguration implements IGeneratorConfiguration<InjectionConfig> {
    // 如果模板引擎是 freemarker
    private String templatePath = "/templates/mapper.xml.ftl";
    // 如果模板引擎是 velocity
    //private String templatePath = "/templates/mapper.xml.vm";
    @Override
    public InjectionConfig config(GeneratorContext generatorContext, InjectionConfig config) {
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                StrategyConfig strategyConfig = generatorContext.getStrategyConfig();
                GlobalConfig globalConfig = generatorContext.getGlobalConfig();
                INameConvert nameConvert = strategyConfig.getNameConvert();
                String entityName = null;
                if (null != nameConvert) {
                    // 自定义处理实体名称
                    entityName = nameConvert.entityNameConvert(tableInfo);
                } else {
                    entityName = NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategyConfig.getNaming(), strategyConfig.getTablePrefix()));
                }
                String mapperName = tableInfo.getEntityName() + "Mapper";
                if(StringUtils.isNotBlank(globalConfig.getXmlName())){
                    mapperName = String.format(globalConfig.getXmlName(), entityName);
                }
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String location = generatorContext.containsKey("mbgp.packageConfig.xml")?
                        generatorContext.get("mbgp.packageConfig.xml").toString():generatorContext.get("mbgp.packageConfig.parent").toString() + ".mapper";
                return  System.getProperty("user.dir") + "/src/main/resources/" + location.replaceAll("\\.","/")
                        + "/" + mapperName + StringPool.DOT_XML;
            }
        });
        config.setFileOutConfigList(focList);
        return config;
    }

    /**
     * 处理表/字段名称
     *
     * @param name     ignore
     * @param strategy ignore
     * @param prefix   ignore
     * @return 根据策略返回处理后的名称
     */
    private String processName(String name, NamingStrategy strategy, Set<String> prefix) {
        String propertyName;
        if (prefix.size() > 0) {
            if (strategy == NamingStrategy.underline_to_camel) {
                // 删除前缀、下划线转驼峰
                propertyName = NamingStrategy.removePrefixAndCamel(name, prefix);
            } else {
                // 删除前缀
                propertyName = NamingStrategy.removePrefix(name, prefix);
            }
        } else if (strategy == NamingStrategy.underline_to_camel) {
            // 下划线转驼峰
            propertyName = NamingStrategy.underlineToCamel(name);
        } else {
            // 不处理
            propertyName = name;
        }
        return propertyName;
    }
}
