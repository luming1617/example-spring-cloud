package spring.cloud.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 用于生成逻辑删除方法
 * 
 * @author liuluming
 *
 */
public class DeleteLogicallyPlugin extends PluginAdapter {

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {

		interfaze.addMethod(generateDeleteByIds(method, introspectedTable));
		interfaze.addMethod(generateDeleteById(method, introspectedTable));

		return true;
	}

	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {

		interfaze.addMethod(generateDeleteByIds(method, introspectedTable));
		interfaze.addMethod(generateDeleteById(method, introspectedTable));

		return true;
	}

	@Override
	public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {

		topLevelClass.addMethod(generateDeleteByIds(method, introspectedTable));
		topLevelClass.addMethod(generateDeleteById(method, introspectedTable));
		return true;
	}

	@Override
	public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {

		topLevelClass.addMethod(generateDeleteByIds(method, introspectedTable));
		topLevelClass.addMethod(generateDeleteById(method, introspectedTable));
		return true;
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

		String tableName = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();// 数据库表名

		XmlElement parentElement = document.getRootElement();
		// 设置sql的类型及Id
		XmlElement deleteByIdsElement = new XmlElement("update");
		deleteByIdsElement.addAttribute(new Attribute("id", "deleteByPrimaryKeysLogically"));
		// 添加注释
		deleteByIdsElement.addElement(new TextElement("<!--"));
		deleteByIdsElement.addElement(new TextElement("  WARNING - @mbggenerated"));
		deleteByIdsElement.addElement(
				new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
		deleteByIdsElement.addElement(new TextElement("-->"));
		// 填充sql
		deleteByIdsElement.addElement(new TextElement("update " + tableName + " set delete_flag = '1' where id in "
				+ " <foreach item=\"item\" index=\"index\" collection=\"ids\" open=\"(\" separator=\",\" close=\")\">#{item}</foreach> "));

		// 设置sql的类型及Id
		XmlElement deleteByIdElement = new XmlElement("update");
		deleteByIdElement.addAttribute(new Attribute("id", "deleteByPrimaryKeyLogically"));
		// 添加注释
		deleteByIdElement.addElement(new TextElement("<!--"));
		deleteByIdElement.addElement(new TextElement("  WARNING - @mbggenerated"));
		deleteByIdElement.addElement(
				new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));
		deleteByIdElement.addElement(new TextElement("-->"));
		// 填充sql
		deleteByIdElement.addElement(new TextElement(
				"update " + tableName + " set delete_flag = '1' where id = " + " #{id,jdbcType=BIGINT}"));

		parentElement.addElement(deleteByIdsElement);
		parentElement.addElement(deleteByIdElement);

		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private Method generateDeleteById(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("deleteByPrimaryKeyLogically");

		m.setVisibility(method.getVisibility());

		m.setReturnType(FullyQualifiedJavaType.getIntInstance());

		// m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(),
		// "deleteFlag", "@Param(\"deleteFlag\")"));
		m.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), "id", "@Param(\"id\")"));

		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}

	private Method generateDeleteByIds(Method method, IntrospectedTable introspectedTable) {

		Method m = new Method("deleteByPrimaryKeysLogically");

		m.setVisibility(method.getVisibility());

		m.setReturnType(FullyQualifiedJavaType.getIntInstance());

		// m.addParameter(new Parameter(FullyQualifiedJavaType.getIntInstance(),
		// "deleteFlag", "@Param(\"deleteFlag\")"));
		m.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.Long[]"), "ids", "@Param(\"ids\")"));

		context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
		return m;
	}

}
