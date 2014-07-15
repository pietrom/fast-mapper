package org.amicofragile.fm;

import java.util.Collection;
import java.util.LinkedList;

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
		StringBuilder mappingCode = new StringBuilder();
		mappingCode.append(String.format("final %s out = new %s();", outClass.getCanonicalName(), outClass.getCanonicalName()));
		for (FieldMapping fm : fieldMappings) {
			mappingCode.append(String.format("out.set%s(in.get%s());\n", capitalize(fm.getFieldOut()), capitalize(fm.getFieldIn())));
		}
		mappingCode.append("return out;");
		String code = String.format("package sm;\n public class %s implements %s<%s, %s> {\n public %s map(%s in) {\n %s }\n}", mapperId,
				ClassMapper.class.getCanonicalName(), inClass.getCanonicalName(), outClass.getCanonicalName(), inClass.getCanonicalName(), outClass.getCanonicalName(), mappingCode.toString());
		return code;
	}

	private String capitalize(String in) {
		return in.substring(0, 1).toUpperCase() + in.substring(1);
	}
	
	public String getMapperId() {
		return mapperId;
	}
}
