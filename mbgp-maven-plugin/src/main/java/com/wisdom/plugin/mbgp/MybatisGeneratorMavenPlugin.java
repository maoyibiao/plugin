package com.wisdom.plugin.mbgp;

import com.wisdom.plugin.mbgp.config.*;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import com.wisdom.plugin.utils.PluginConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author maoyibiao
 * maven plugin插件主类
 */
@Mojo( name = "generator")
public class MybatisGeneratorMavenPlugin extends AbstractMojo {
    @Parameter(
            property = "project",
            required = true,
            readonly = true
    )
    protected MavenProject project;
    @Parameter
    private String globalConfiguration;
    @Parameter
    private String dataSourceConfiguration;
    @Parameter
    private String injectionConfiguration;
    @Parameter
    private String packageConfiguration;
    @Parameter
    private String strategyConfiguration;
    @Parameter
    private String templateConfiguartion;
    @Parameter(defaultValue = "/application.properties,/application.yml")
    private String properties;

    private final static String output = System.getProperty("user.dir") + "/src/main/java";

    private GeneratorContext initContext(PluginConfig p) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GlobalConfiguration gc = StringUtils.isNotBlank(globalConfiguration)?
                (GlobalConfiguration) Class.forName(globalConfiguration).newInstance():new GlobalConfiguration();
        DataSourceConfiguration dc = StringUtils.isNoneBlank(dataSourceConfiguration)?
                (DataSourceConfiguration) Class.forName(dataSourceConfiguration).newInstance():new DataSourceConfiguration();
        InjectionConfiguration ic = StringUtils.isNotBlank(injectionConfiguration)?
                (InjectionConfiguration) Class.forName(injectionConfiguration).newInstance():new InjectionConfiguration();
        PackageConfiguration pc = StringUtils.isNotBlank(packageConfiguration)?
                (PackageConfiguration) Class.forName(packageConfiguration).newInstance():new PackageConfiguration();
        StrategyConfiguration sc = StringUtils.isNotBlank(strategyConfiguration)?
                (StrategyConfiguration) Class.forName(strategyConfiguration).newInstance():new StrategyConfiguration();
        TemplateConfiguartion tc = StringUtils.isNotBlank(templateConfiguartion)?
                (TemplateConfiguartion) Class.forName(templateConfiguartion).newInstance():new TemplateConfiguartion();
        return new GeneratorContext(gc,dc,ic,pc,sc,tc,p);
    }

    private PluginConfig initProperties() throws IOException {
        String[] propertiesArray = properties.split(",");
        List<Resource> resources = this.project.getResources();
        Set<String> resourceDirectories = new HashSet<>();
        for(Resource resource:resources){
            for(String ps:propertiesArray){
                resourceDirectories.add(resource.getDirectory() + ps);
            }
        }
        PluginConfig p = PluginConfig.load(new PluginConfig.FileCallBack() {
            @Override
            public void invok(String path, PluginConfig pluginConfig) {
                if(pluginConfig.containsKey("spring.profiles.active")){
                    String active = pluginConfig.get("spring.profiles.active").toString();
                    int index = path.indexOf(".");
                    String activePath = path.substring(0,index) + "-" +active + "." + path.substring(index+1);
                    try {
                        pluginConfig.loadFile(null,activePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        },resourceDirectories.toArray(new String[resourceDirectories.size()]));
        //未配置的情况读取spring的datasource配置
        if(!p.containsKey("mbgp.dataSourceConfig.url") && p.containsKey("spring.datasource.url")){
            p.put("mbgp.dataSourceConfig.url",p.get("spring.datasource.url"));
        }
        if(!p.containsKey("mbgp.dataSourceConfig.driverName") && p.containsKey("spring.datasource.driver-class-name")){
            p.put("mbgp.dataSourceConfig.driverName",p.get("spring.datasource.driver-class-name"));
        }
        if(!p.containsKey("mbgp.dataSourceConfig.username") && p.containsKey("spring.datasource.username")){
            p.put("mbgp.dataSourceConfig.username",p.get("spring.datasource.username"));
        }
        if(!p.containsKey("mbgp.dataSourceConfig.password") && p.containsKey("spring.datasource.password")){
            p.put("mbgp.dataSourceConfig.password",p.get("spring.datasource.password"));
        }
        return p;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            GeneratorContext generatorContext = this.initContext(this.initProperties());
            generatorContext.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
