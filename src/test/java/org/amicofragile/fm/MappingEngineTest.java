package org.amicofragile.fm;

import static org.junit.Assert.*;

import org.amicofragile.fm.fixtures.Cyclist;
import org.amicofragile.fm.fixtures.Person;
import org.junit.Test;

public class MappingEngineTest {
	private static final Person EDDY = new Person("Eddy", "Merckx");
	@Test
	public void mapWithoutMappingReturnsNull() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final Person result = engine.map(EDDY, Person.class);
		assertNull(result);
	}
	
	@Test
	public void mapWithClassLevelMapper() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final ClassMapper<Person, Cyclist> mapper = new PersonToCyclistMapper();
		engine.registerMapper(Person.class, Cyclist.class, mapper);
		Cyclist cyclist = engine.map(EDDY, Cyclist.class);
		assertEquals("EDDY MERCKX", cyclist.getFullName());
	}
	
	@Test
	public void mapByDeclarativeMapping() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final MapperFactory mapperFactory = new MapperFactory();
		final String mapperId = "cyclist2cyclist";
		final DeclarativeMapperConfiguration mapperConfig = new DeclarativeMapperConfiguration(mapperId, Person.class, Person.class);
		mapperConfig.addSimpleMapping("firstName", "surname");
		mapperConfig.addSimpleMapping("surname", "firstName");
		engine.registerMapper(mapperId, mapperFactory.createMapper(mapperConfig));
		Person mapped = engine.map(mapperId, EDDY);
		assertEquals("Eddy", mapped.getSurname());
		assertEquals("Merckx", mapped.getFirstName());
	}
}
