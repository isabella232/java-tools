/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * This is an experimental util that provides quite fancy map initialization.
 * <p>
 * <strong>CAUTION:</strong> performance of this method is not sufficient for high repetitive operations.</strong>
 * </p>
 * <p>
 * Wouldn't it be great if we could create Java maps like this?
 * </p>
 * <pre>
 * Map<String, Integer> map = mapOf(
 *      one -> 1,
 *      two -> 2
 * );
 * </pre>
 * <p>
 * Here you are!
 * </p>
 * <p>
 * The following conditions are required for this function to work properly:
 * <ul>
 * <li>Java 1.8.0_80+</li>
 * <li>Compiled with <code>-parameter</code> flag</li>
 * </ul>
 * </p>
 * <p>
 * Credits to Per-Åke Minborg.
 * </p>
 *
 * @param <VALUE> map entry value type
 * @see <a href="http://minborgsjavapot.blogspot.ch/2016/12/creating-maps-with-named-lambdas.html" >Creating Maps With Named Lambdas</a>
 */
@FunctionalInterface
public interface MapCreator<VALUE> extends Function<String, VALUE>, Serializable {

	String JAVA_VERSION = Runtime.class.getPackage().getImplementationVersion();
	String MIN_JAVA_VERSION = "1.8.0_80";

	default String key() {
		return functionalMethod().getParameters()[0].getName();
	}

	default VALUE value() {
		return apply(key());
	}

	default Method functionalMethod() {
		final SerializedLambda serialzedLabmda = serializedLambda();
		final Class<?> implementationClass = implementationClass(serialzedLabmda);
		return Stream.of(implementationClass.getDeclaredMethods())
		             .filter(m -> Objects.equals(m.getName(), serialzedLabmda.getImplMethodName()))
		             .findFirst()
		             .orElseThrow(RuntimeException::new);
	}

	default Class<?> implementationClass(SerializedLambda serializedLambda) {
		try {
			final String className = serializedLambda.getImplClass().replaceAll("/", ".");
			return Class.forName(className);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	default SerializedLambda serializedLambda() {
		try {
			final Method replaceMethod = getClass().getDeclaredMethod("writeReplace");
			replaceMethod.setAccessible(true);
			return (SerializedLambda) replaceMethod.invoke(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * <strong>CAUTION:</strong> performance of this method is not sufficient for high repetitive operations.</strong>
	 * <p>
	 * Wouldn't it be great if we could create Java maps like this?
	 * </p>
	 * <pre>
	 * Map<String, Integer> map = mapOf(
	 *      one -> 1,
	 *      two -> 2
	 * );
	 * </pre>
	 * <p>
	 * Here you are!
	 * </p>
	 * <p>
	 * The following conditions are required for this function to work properly:
	 * <ul>
	 * <li>Java 1.8.0_80+</li>
	 * <li>Compiled with <code>-parameter</code> flag</li>
	 * </ul>
	 * </p>
	 *
	 * @param mappings lambda expressions that express map creation.
	 * @param <VALUE>  value type of map entry
	 * @return initialized map.
	 */
	@SafeVarargs
	static <VALUE> Map<String, VALUE> mapOf(MapCreator<VALUE>... mappings) {
		try {
			return Stream.of(mappings).collect(toMap(MapCreator::key, MapCreator::value));
		} catch (RuntimeException e) {
			checkVersion(e);
			throw e;
		}
	}

	static void checkVersion(RuntimeException e) throws UnsupportedOperationException {
		DefaultArtifactVersion minVersion = new DefaultArtifactVersion(MIN_JAVA_VERSION);
		DefaultArtifactVersion version = new DefaultArtifactVersion(JAVA_VERSION);

		if (version.compareTo(minVersion) < 0) {
			throw new UnsupportedOperationException("Java Version " + JAVA_VERSION + " is not compatible use at least " + MIN_JAVA_VERSION, e);
		}
	}
}
