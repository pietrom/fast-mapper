package org.amicofragile.fm;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class MapperFactory {
	public ClassMapper createMapper(DeclarativeMapperConfiguration declarativeMapperConfiguration) throws Exception {
		final String code = declarativeMapperConfiguration.getClassMapperCode();
		System.out.println(code);
		final File tempDir = new File(System.getProperty("java.io.tmpdir") + "/sources");
		final File pkg = new File(tempDir, "sm");
		pkg.mkdirs();
		final File source = new File(pkg, declarativeMapperConfiguration.getMapperId() + ".java");
		new FileWriter(source).append(code).close();

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, source.getPath());

		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { tempDir.toURI().toURL() });
		Class<ClassMapper> cls = (Class<ClassMapper>) Class.forName("sm." + declarativeMapperConfiguration.getMapperId(), true, classLoader);
		ClassMapper mapper = cls.newInstance();

		return mapper;
	}
}
