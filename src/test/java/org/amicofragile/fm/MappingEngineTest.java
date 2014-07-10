package org.amicofragile.fm;

import static org.junit.Assert.*;

import org.amicofragile.fm.fixtures.Person;
import org.junit.Test;

public class MappingEngineTest {
	private static final Person EDDY = new Person("Eddy", "Merckx");
	@Test
	public void mapWithoutMappingIsIdentity() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final Object result = engine.map(EDDY);
		assertTrue(EDDY == result);
	}
}
