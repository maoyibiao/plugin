package com.wisdom.plugin.mbg;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.util.ClassloaderUtility;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    @Parameter(
            property = "mybatis.generator.configurationFile",
            defaultValue = "${project.basedir}/src/main/resources/generatorConfig.xml",
            required = true
    )
    private File configurationFile;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            List<Resource> resources = this.project.getResources();
            List<String> resourceDirectories = new ArrayList();
            for(Resource resource:resources){
                resourceDirectories.add(resource.getDirectory());
            }
            ClassLoader cl = ClassloaderUtility.getCustomClassloader(resourceDirectories);
            ObjectFactory.addExternalClassLoader(cl);
            //MBG 执行过程中的警告信息
            List<String> warnings = new ArrayList<String>();
            //当生成的代码重复时，覆盖原代码
            boolean overwrite = true;
            //读取我们的 MBG 配置文件
            ConfigurationParser cp = new ConfigurationParser(this.project.getProperties(),warnings);
            Configuration config = null;
            if(this.configurationFile.exists()){
                config = cp.parseConfiguration(this.configurationFile);
                getLog().info("读取配置文件:" + this.configurationFile.getPath());
            }else{
                InputStream is = this.getClass().getResourceAsStream("/generatorConfig.xml");
                getLog().info("读取配置文件:" + this.getClass().getResource("/generatorConfig.xml").getPath());
                config = cp.parseConfiguration(is);
                is.close();
            }
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            //创建 MBG
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //输出警告信息
            for (String warning : warnings) {
                getLog().warn( warning );
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
