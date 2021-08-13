package com.wisdom.plugin.mbg;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.XmlConstants;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 *  @author maoyibiao
 *  自定义空白XmlGenerator生成器
 */
public class BlankXMLMapperGenerator extends AbstractXmlGenerator {
    private String mapperName;

    private String targetPackage;
    public BlankXMLMapperGenerator() {
        super();
    }

    protected XmlElement getSqlMapElement() {
        progressCallback.startTask(getString("Progress.12", mapperName)); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = targetPackage + "." + mapperName;
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);
        return answer;
    }

    public Document getDocument(String mapperName,String targetPackage){
        this.mapperName = mapperName;
        this.targetPackage = targetPackage;
        return this.getDocument();
    }
    @Override
    public Document getDocument() {
        Document document = new Document(
                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());

      /*if (!context.getPlugins().sqlMapDocumentGenerated(document,
                introspectedTable)) {
            document = null;
        }*/

        return document;
    }
}
