package com.wisdom.plugin.mbg.plugin;

import com.wisdom.plugin.mbg.generator.BlankXMLMapperGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.internal.NullProgressCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author maoyibiao
 * 扩展mapper生成
 */
public class MybatisMapperExtPlugin extends PluginAdapter {
    private final static String suffix = "Ext";
    private BlankXMLMapperGenerator xmlMapperGenerator;
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> javaFiles = new ArrayList<>(super.contextGenerateAdditionalJavaFiles(introspectedTable));
        if(introspectedTable.getGeneratedJavaFiles().size() == 3){
            GeneratedJavaFile generatedJavaFile = introspectedTable.getGeneratedJavaFiles().get(2);
            CompilationUnit compilationUnit = generatedJavaFile.getCompilationUnit();
            Interface itf = new Interface(new FullyQualifiedJavaType(compilationUnit.getType().getFullyQualifiedName()+ suffix));
            itf.setVisibility(JavaVisibility.PUBLIC);
            GeneratedJavaFile javaFile = new GeneratedJavaFile(itf,generatedJavaFile.getTargetProject(),generatedJavaFile.getFileEncoding(),this.context.getJavaFormatter());
            String filePath = javaFile.getTargetProject() + "/" + javaFile.getTargetPackage().replaceAll("\\.","/") + "/" +javaFile.getFileName();
            File file = new File(filePath);
            if(!file.exists()) {
                javaFiles.add(javaFile);
            }
        }
        return javaFiles;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        List<GeneratedXmlFile> generatedXmlFiles = new ArrayList<>(super.contextGenerateAdditionalXmlFiles(introspectedTable));
        if(xmlMapperGenerator == null){
            xmlMapperGenerator = new BlankXMLMapperGenerator();
            xmlMapperGenerator.setContext(this.context);
            xmlMapperGenerator.setIntrospectedTable(introspectedTable);
            xmlMapperGenerator.setProgressCallback(new NullProgressCallback());
            xmlMapperGenerator.setWarnings(new ArrayList<>());
        }
        for(GeneratedXmlFile generatedXmlFile : introspectedTable.getGeneratedXmlFiles()) {
            String fileName = generatedXmlFile.getFileName();
            String xmlName = fileName.substring(0,fileName.lastIndexOf(".")) + suffix;
            fileName = xmlName + ".xml";
            GeneratedXmlFile gxf = new GeneratedXmlFile(xmlMapperGenerator.getDocument(xmlName,generatedXmlFile.getTargetPackage()),fileName, generatedXmlFile.getTargetPackage(),generatedXmlFile.getTargetProject(),false,this.context.getXmlFormatter());
            String filePath = generatedXmlFile.getTargetProject() + "/" + generatedXmlFile.getTargetPackage().replaceAll("\\.","/") + "/" + fileName;
            File file = new File(filePath);
            if(!file.exists()) {
                generatedXmlFiles.add(gxf);
            }
        }
        return generatedXmlFiles;
    }
}
