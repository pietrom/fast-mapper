package org.amicofragile.fm;

import static org.junit.Assert.assertTrue;

import org.amicofragile.fm.fixtures.BigObject;
import org.amicofragile.fm.fixtures.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

public class PerformanceTest {
	private static final int ITERATIONS = 1000;
	private static final double NANOS_PER_MILLI = 1000000.0;
	private static final int RATIO = 1000;
	private static final Person EDDY = new Person("Eddy", "Merckx");

	@Test
	public void onTheFlyCompiledMapperIsFasterThanReflection() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final String mapperId = "cyclist2cyclist";
		final DeclarativeMapperConfiguration mapperConfig = new DeclarativeMapperConfiguration(mapperId, Person.class, Person.class);
		mapperConfig.addSimpleMapping("firstName", "surname");
		mapperConfig.addSimpleMapping("surname", "firstName");
		engine.registerMapper(mapperId, mapperConfig);
		long start = System.nanoTime();
		for (int i = 0; i < ITERATIONS; i++) {
			Person mapped = engine.map(mapperId, EDDY);
		}
		long stop = System.nanoTime();
		final double fastMapperElapsed = (stop - start) / (NANOS_PER_MILLI * ITERATIONS);
		System.out.printf("fastMapperElapsed = %4f\n", fastMapperElapsed);
		final String targetClassName = Person.class.getCanonicalName();
		start = System.nanoTime();
		for (int i = 0; i < ITERATIONS; i++) {
			Person mapped = (Person) Class.forName(targetClassName).newInstance();
			BeanUtils.setProperty(mapped, "surname", BeanUtils.getProperty(EDDY, "firstName"));
			BeanUtils.setProperty(mapped, "firstName", BeanUtils.getProperty(EDDY, "surname"));
		}
		stop = System.nanoTime();
		final double reflectionElapsed = (stop - start) / (NANOS_PER_MILLI * ITERATIONS);
		System.out.printf("fastMapperElapsed * %d = %4f\n", RATIO, fastMapperElapsed * RATIO);
		System.out.printf("reflectionElapsed = %4f\n", reflectionElapsed);
		assertTrue((fastMapperElapsed * 100) < reflectionElapsed);
	}
	
	@Test
	public void mapBigObject() throws Exception {
		final MappingEngine engine = new MappingEngine();
		final String mapperId = "big2big";
		final DeclarativeMapperConfiguration mapperConfig = new DeclarativeMapperConfiguration(mapperId, BigObject.class, BigObject.class);
		for(int i = 1; i <= 30; i++) {
			final String attr = String.format("attr%02d", i);
			mapperConfig.addSimpleMapping(attr, attr);
		}
		engine.registerMapper(mapperId, mapperConfig);
		
		final BigObject input = new BigObject("A_VALUE");
		long start = System.nanoTime();
		for (int i = 0; i < ITERATIONS; i++) {
			BigObject mapped = engine.map(mapperId, input);
		}
		long stop = System.nanoTime();
		final double fastMapperElapsed = (stop - start) / (NANOS_PER_MILLI * ITERATIONS);
		System.out.printf("fastMapperElapsed = %4f\n", fastMapperElapsed);
		final String targetClassName = Person.class.getCanonicalName();
		start = System.nanoTime();
		for (int i = 0; i < ITERATIONS; i++) {
			Person mapped = (Person) Class.forName(targetClassName).newInstance();
			for(int j = 1; i <= 30; i++) {
				final String attr = String.format("attr%02d", j);
				BeanUtils.setProperty(mapped, attr, BeanUtils.getProperty(input, attr));
			}
		}
		stop = System.nanoTime();
		final double reflectionElapsed = (stop - start) / (NANOS_PER_MILLI * ITERATIONS);
		System.out.printf("fastMapperElapsed * %d = %4f\n", RATIO, fastMapperElapsed * RATIO);
		System.out.printf("reflectionElapsed = %4f\n", reflectionElapsed);
		assertTrue((fastMapperElapsed * 1) < reflectionElapsed);
	}
}
