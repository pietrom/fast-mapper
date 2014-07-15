package org.amicofragile.fm;

import java.util.HashMap;
import java.util.Map;

import org.amicofragile.fm.fixtures.Cyclist;
import org.amicofragile.fm.fixtures.Person;

public class MappingEngine {
	private final Map<Class, Map<Class, ClassMapper>> mappers;
	private final Map<String, ClassMapper> mappersById;
	
	public MappingEngine() {
		mappers = new HashMap<Class, Map<Class, ClassMapper>>();
		mappersById = new HashMap<String, ClassMapper>();
	}
	
	public <InT, OutT> OutT map(InT input, Class<OutT> outClass) {
		OutT result = null;
		ClassMapper<InT, OutT> mapper = (ClassMapper<InT, OutT>) getMapperFor(input.getClass(), outClass);
		if(mapper != null) {
			result = mapper.map(input);
		}
		return result;
	}

	public <InT, OutT> void registerMapper(Class<InT> inClass, Class<OutT> outClass, ClassMapper<InT, OutT> mapper) {
		Map<Class, ClassMapper> inputMappers;
		if(mappers.containsKey(inClass)) {
			inputMappers = mappers.get(inClass);
		} else {
			inputMappers = new HashMap<Class, ClassMapper>();
			mappers.put(inClass, inputMappers);
		}
		inputMappers.put(outClass, mapper);
	}
	
	private <InT, OutT> ClassMapper<InT, OutT> getMapperFor(Class<InT> inClass, Class<OutT> outClass) {
		ClassMapper result = null;
		Map<Class, ClassMapper> inputMappers;
		if(mappers.containsKey(inClass)) {
			inputMappers = mappers.get(inClass);
			result = inputMappers.get(outClass);
		}
		return result;
	}

	public void registerMapper(String mapperId, ClassMapper mapper) {
		mappersById.put(mapperId, mapper);
	}

	public <InT, OutT> OutT map(String mapperId, InT eddy) {
		final ClassMapper<InT, OutT> mapper = mappersById.get(mapperId);
		return mapper.map(eddy);
	}
}
