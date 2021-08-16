package com.wisdom.plugin.mbgp.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.wisdom.plugin.mbgp.context.GeneratorContext;

import java.util.ArrayList;
import java.util.List;

public class InjectionConfiguration implements IGeneratorConfiguration<InjectionConfig> {
    @Override
    public InjectionConfig config(GeneratorContext generatorContext, InjectionConfig config) {
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                String location = generatorContext.containsKey("mbg.packageConfig.xml")?
                        generatorContext.get("mbg.packageConfig.xml").toString():generatorContext.get("mbg.packageConfig.parent").toString() + ".mapper";
                return  System.getProperty("user.dir") + "/src/main/resources/" + location.replaceAll("\\.","/")
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        config.setFileOutConfigList(focList);
        return config;
    }
}
