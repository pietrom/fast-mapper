package org.amicofragile.fm;

import java.util.Collection;
import java.util.LinkedList;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public class DeclarativeMapperConfiguration {
	private final String mapperId;
	private final Class inClass, outClass;
	private final Collection<FieldMapping> fieldMappings;

	public DeclarativeMapperConfiguration(String mapperId, Class inClass, Class outClass) {
		this.mapperId = mapperId;
		this.inClass = inClass;
		this.outClass = outClass;
		fieldMappings = new LinkedList<FieldMapping>();
	}

	public void addSimpleMapping(String fieldIn, String fieldOut) {
		fieldMappings.add(new FieldMapping(fieldIn, fieldOut));
	}

	public Collection<FieldMapping> getFieldMappings() {
		return fieldMappings;
	}

	public String getClassMapperCode() {
		final ST st = loadTemplateGroup().getInstanceOf("classMapper");
		st.add("mapperId", mapperId);
		st.add("mapperInterface", ClassMapper.class.getCanonicalName());
		st.add("inClass", inClass.getCanonicalName());
		st.add("outClass", outClass.getCanonicalName());
		st.add("fieldMappings", fieldMappings);
		String code = st.render();
		return code;
		/*
		StringBuilder mappingCode = new StringBuilder();
		mappingCode.append(String.format("final %s out = new %s();", outClass.getCanonicalName(), outClass.getCanonicalName()));
		for (FieldMapping fm : fieldMappings) {
			mappingCode.append(String.format("out.set%s(in.get%s());\n", capitalize(fm.getFieldOut()), capitalize(fm.getFieldIn())));
		}
		mappingCode.append("return out;");
		String code = String.format("package sm;\n public class %s implements %s<%s, %s> {\n public %s map(%s in) {\n %s }\n}", mapperId,
				ClassMapper.class.getCanonicalName(), inClass.getCanonicalName(), outClass.getCanonicalName(), inClass.getCanonicalName(), outClass.getCanonicalName(), mappingCode.toString());
		return code;
		*/
	}

	public String getMapperId() {
		return mapperId;
	}
	
	private STGroup loadTemplateGroup() {
		final STGroup group = new STGroupFile(DeclarativeMapperConfiguration.class.getResource("/class-mapper.stg"), "UTF-8", '<', '>');
		group.registerRenderer(String.class, new StringRenderer());
		return group;
	}
}
