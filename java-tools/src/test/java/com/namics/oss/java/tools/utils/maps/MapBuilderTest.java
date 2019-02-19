/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MapBuilderTest.
 *
 * @author aschaefer, Namics AG
 * @since 13.06.14 11:00
 */
class MapBuilderTest {

	@Test
	void testSpecificMap() {
		assertTrue(new MapBuilder<String, String>(new TreeMap<String, String>().getClass()).map() instanceof TreeMap);
		assertTrue(new MapBuilder<String, String>(TreeMap.class).map() instanceof TreeMap);
		assertTrue(new MapBuilder<String, String>(new TreeMap<String, String>()).map() instanceof TreeMap);

		TreeMap<String, String> instances = new TreeMap<String, String>();
		assertTrue(instances == new MapBuilder<String, String>(instances).map());
	}

	@Test
	void testPut() throws Exception {
		MapBuilder<String, String> test = new MapBuilder<String, String>()
				.put("Test", "Test")
				.put("Test2", "Test");
		Map<String, String> map = test.map();
		assertNotNull(map);
		assertEquals(2, map.size());
	}

	@Test
	void testMap() throws Exception {
		MapBuilder<String, String> test = new MapBuilder<String, String>()
				.put("Test", "Test")
				.put("Test2", "Test");
		Map<String, String> map = test.map();
		assertNotNull(map);
		assertEquals(2, map.size());
	}

	@Test
	void testImmutable() throws Exception {
		Map<String, String> map = new MapBuilder<String, String>()
				.put("KEY", "VALUE")
				.put("ANOTHER", "VALUE")
				.immutable();
		assertNotNull(map);
		assertEquals(2, map.size());
		assertThrows(UnsupportedOperationException.class, () -> map.put("Immutable", "cannot add"));
	}

	@Test
	void testImmutableUnderlyingMap() throws Exception {
		MapBuilder<String, String> builder = new MapBuilder<String, String>();
		Map<String, String> map = builder
				.put("KEY", "VALUE")
				.put("ANOTHER", "VALUE")
				.immutable();
		builder.put("test", "test");
		assertEquals(2, map.size());
		assertEquals(3, builder.immutable().size());
	}

	@Test
	void testSorted() throws Exception {
		MapBuilder<String, String> test = new MapBuilder<String, String>()
				.put("Test", "Test")
				.put("Test2", "Test");
		Map<String, String> map = test.sorted();
		assertTrue(map instanceof SortedMap);
		assertEquals(2, map.size());

		TreeMap<String, String> instances = new TreeMap<String, String>();
		assertTrue(instances == new MapBuilder<String, String>(instances).sorted());
	}
}
