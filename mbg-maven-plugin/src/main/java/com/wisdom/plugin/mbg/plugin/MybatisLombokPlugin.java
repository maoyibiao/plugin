package com.wisdom.plugin.mbg.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author maoyibiao
 * 添加Po的lombok注解
 */
public class MybatisLombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("lombok.NoArgsConstructor");
        topLevelClass.addImportedType("lombok.AllArgsConstructor");
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");
        //topLevelClass.addImportedType("lombok.Getter");
        //topLevelClass.addImportedType("lombok.Setter");
        //topLevelClass.addImportedType("lombok.ToString");
        //topLevelClass.addAnnotation("@Getter");
        //topLevelClass.addAnnotation("@Setter");
        //topLevelClass.addAnnotation("@ToString");
        return true;
    }

}
