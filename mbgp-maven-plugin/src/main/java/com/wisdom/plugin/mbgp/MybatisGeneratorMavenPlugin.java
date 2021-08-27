package com.wisdom.plugin.mbgp;

import com.wisdom.plugin.mbgp.configuration.*;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import com.wisdom.plugin.utils.PluginConfig;
import com.wisdom.plugin.utils.SpiUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

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
    @Parameter(defaultValue = "/application.properties,/application.yml")
    private String properties;

    private final static String output = System.getProperty("user.dir") + "/src/main/java";

    private GeneratorContext initContext(PluginConfig p) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GlobalConfiguration gc = SpiUtils.loadDefault(GlobalConfiguration.class);
        DataSourceConfiguration dc = SpiUtils.loadDefault(DataSourceConfiguration.class);
        PackageConfiguration pc = SpiUtils.loadDefault(PackageConfiguration.class);
        StrategyConfiguration sc = SpiUtils.loadDefault(StrategyConfiguration.class);
        TemplateConfiguartion tc = SpiUtils.loadDefault(TemplateConfiguartion.class);
        List<AbstractInjectionConfiguration> ics = SpiUtils.loadBeans(AbstractInjectionConfiguration.class);
        ics.add(SpiUtils.loadDefault(JunitTestConfiguration.class));
        return new GeneratorContext(gc,dc,ics,pc,sc,tc,p);
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
