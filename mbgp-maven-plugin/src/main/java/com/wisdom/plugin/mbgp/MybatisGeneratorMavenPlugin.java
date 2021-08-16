package com.wisdom.plugin.mbgp;

import com.wisdom.plugin.mbgp.config.*;
import com.wisdom.plugin.mbgp.context.GeneratorContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
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
    @Parameter(defaultValue = "/application.properties")
    private String properties;

    private final static String output = System.getProperty("user.dir") + "/src/main/java";

    private GeneratorContext initContext(Properties properties) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
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
        return new GeneratorContext(gc,dc,ic,pc,sc,tc,properties);
    }

    private Properties initProperties(){
        Properties p = new Properties();
        List<Resource> resources = this.project.getResources();
        Set<String> resourceDirectories = new HashSet<>();
        for(Resource resource:resources){
            File file = new File(resource.getDirectory() + properties);
            if(file.exists()){
                try {
                    p.load(new FileInputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //未配置的情况读取spring的datasource配置
        if(!p.containsKey("mbg.dataSourceConfig.url") && p.containsKey("spring.datasource.url")){
            p.setProperty("mbg.dataSourceConfig.url",p.getProperty("spring.datasource.url"));
        }
        if(!p.containsKey("mbg.dataSourceConfig.driverName") && p.containsKey("spring.datasource.driver-class-name")){
            p.setProperty("mbg.dataSourceConfig.driverName",p.getProperty("spring.datasource.driver-class-name"));
        }
        if(!p.containsKey("mbg.dataSourceConfig.username") && p.containsKey("spring.datasource.username")){
            p.setProperty("mbg.dataSourceConfig.username",p.getProperty("spring.datasource.username"));
        }
        if(!p.containsKey("mbg.dataSourceConfig.password") && p.containsKey("spring.datasource.password")){
            p.setProperty("mbg.dataSourceConfig.password",p.getProperty("spring.datasource.password"));
        }
        return p;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            GeneratorContext generatorContext = this.initContext(this.initProperties());
            generatorContext.exec();
        } catch (Exception e) {
            getLog().error(e);
        }

    }
}
