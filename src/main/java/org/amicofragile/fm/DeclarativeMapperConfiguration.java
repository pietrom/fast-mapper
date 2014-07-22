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
