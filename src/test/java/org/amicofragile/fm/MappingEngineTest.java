package org.amicofragile.fm;

import static org.junit.Assert.*;

import org.amicofragile.fm.fixtures.Person;
import org.junit.Test;

public class MappingEngineTest {
	private static final Person EDDY = new Person("Eddy", "Merckx");
	@Test
	public void mapWithoutMappingIsIdentity() throws Exception {
		final MappingEngine mapper = new MappingEngine();
		final Object result = mapper.map(EDDY);
		assertTrue(EDDY == result);
	}
}
