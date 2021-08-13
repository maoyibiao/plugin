# plugin 插件

## mbg-maven-plugin

Mybatis Generator Maven Plugin插件，添加maven plugin依赖后可在idea的maven plugin中运行mbg:generator可自动生成代码

### 默认配置的生成内容：

1）生成Po增加lombok的@Data、@NoArgsConstructor、@AllArgsConstructor注解

2）生成Po读取数据表的字段备注信息并添加Swagger注解@ApiModelProperty(value = "分类图标")

3）开发过程中多次数据库结构的调整重新生成可能会有文件覆盖或追加的问题，添加了MapperExt的扩展文件生成，开发者的扩展可以在该类和XML中进行编写。其中二次生成的po,mapper会覆盖mapperExt不会覆盖

4）如需要调整配置也可以在Resource下添加自己的generatorConfig.xml

### 配置

1）上传至Maven仓库 

2）创建SpringBoot项目并配置pom和properties


pom.xml

````
            <plugin>
                <groupId>com.wisdom.plugin</groupId>
                <artifactId>mbg-maven-plugin</artifactId>
                <version>${project.version}</version>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql-connector.version}</version>
                    </dependency>
                </dependencies>
            </plugin>

````

application.properties

````
spring.datasource.url=jdbc:mysql://192.168.160.224:3306/mall?useUnicode=true&characterEncoding=utf8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
mbg.javaModelGenerator.targetPackage=com.wisdom.plugin.demo.mbg.po
mbg.sqlMapGenerator.targetPackage=com.wisdom.plugin.demo.mbg.mapper
mbg.javaClientGenerator.targetPackage=com.wisdom.plugin.demo.mbg.mapper
````
