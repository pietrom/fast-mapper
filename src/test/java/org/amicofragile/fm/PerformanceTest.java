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
		final String targetClassName = BigObject.class.getCanonicalName();
		start = System.nanoTime();
		for (int i = 0; i < ITERATIONS; i++) {
			BigObject mapped = (BigObject) Class.forName(targetClassName).newInstance();
			BeanUtils.setProperty(mapped, "attr01", BeanUtils.getProperty(input, "attr01"));
			BeanUtils.setProperty(mapped, "attr02", BeanUtils.getProperty(input, "attr02"));
			BeanUtils.setProperty(mapped, "attr03", BeanUtils.getProperty(input, "attr03"));
			BeanUtils.setProperty(mapped, "attr04", BeanUtils.getProperty(input, "attr04"));
			BeanUtils.setProperty(mapped, "attr05", BeanUtils.getProperty(input, "attr05"));
			BeanUtils.setProperty(mapped, "attr06", BeanUtils.getProperty(input, "attr06"));
			BeanUtils.setProperty(mapped, "attr07", BeanUtils.getProperty(input, "attr07"));
			BeanUtils.setProperty(mapped, "attr08", BeanUtils.getProperty(input, "attr08"));
			BeanUtils.setProperty(mapped, "attr09", BeanUtils.getProperty(input, "attr09"));
			BeanUtils.setProperty(mapped, "attr10", BeanUtils.getProperty(input, "attr10"));
			BeanUtils.setProperty(mapped, "attr11", BeanUtils.getProperty(input, "attr11"));
			BeanUtils.setProperty(mapped, "attr12", BeanUtils.getProperty(input, "attr12"));
			BeanUtils.setProperty(mapped, "attr13", BeanUtils.getProperty(input, "attr13"));
			BeanUtils.setProperty(mapped, "attr14", BeanUtils.getProperty(input, "attr14"));
			BeanUtils.setProperty(mapped, "attr15", BeanUtils.getProperty(input, "attr15"));
			BeanUtils.setProperty(mapped, "attr16", BeanUtils.getProperty(input, "attr16"));
			BeanUtils.setProperty(mapped, "attr17", BeanUtils.getProperty(input, "attr17"));
			BeanUtils.setProperty(mapped, "attr18", BeanUtils.getProperty(input, "attr18"));
			BeanUtils.setProperty(mapped, "attr19", BeanUtils.getProperty(input, "attr19"));
			BeanUtils.setProperty(mapped, "attr20", BeanUtils.getProperty(input, "attr20"));
			BeanUtils.setProperty(mapped, "attr21", BeanUtils.getProperty(input, "attr21"));
			BeanUtils.setProperty(mapped, "attr22", BeanUtils.getProperty(input, "attr22"));
			BeanUtils.setProperty(mapped, "attr23", BeanUtils.getProperty(input, "attr23"));
			BeanUtils.setProperty(mapped, "attr24", BeanUtils.getProperty(input, "attr24"));
			BeanUtils.setProperty(mapped, "attr25", BeanUtils.getProperty(input, "attr25"));
			BeanUtils.setProperty(mapped, "attr26", BeanUtils.getProperty(input, "attr26"));
			BeanUtils.setProperty(mapped, "attr27", BeanUtils.getProperty(input, "attr27"));
			BeanUtils.setProperty(mapped, "attr28", BeanUtils.getProperty(input, "attr28"));
			BeanUtils.setProperty(mapped, "attr29", BeanUtils.getProperty(input, "attr29"));
			BeanUtils.setProperty(mapped, "attr30", BeanUtils.getProperty(input, "attr30"));
		}
		stop = System.nanoTime();
		final int ratio = 100;
		final double reflectionElapsed = (stop - start) / (NANOS_PER_MILLI * ITERATIONS);
		System.out.printf("fastMapperElapsed * %d = %4f\n", ratio, fastMapperElapsed * ratio);
		System.out.printf("reflectionElapsed = %4f\n", reflectionElapsed);
		assertTrue((fastMapperElapsed * ratio) < reflectionElapsed);
	}
}
