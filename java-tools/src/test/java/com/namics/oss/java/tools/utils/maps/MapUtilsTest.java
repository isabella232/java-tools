/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MapUtilsTest.
 *
 * @author aschaefer, Namics AG
 * @since 13.06.14 10:49
 */
class MapUtilsTest {

	@Test
	void testPutMap() throws Exception {
		Map<String, String> map = MapUtils.put("KEY", "VALUE")
		                                  .put("ANOTHER", "VALUE")
		                                  .map();
		assertNotNull(map);
		assertEquals(2, map.size());
	}

	@Test
	void testPutNullTyped() throws Exception {
		Map<String, String> map = MapUtils.<String, String>put(null, null)
				.put("ANOTHER", "VALUE")
				.map();
		assertNotNull(map);
		assertEquals(2, map.size());
	}

	@Test
	void testPutUntyped() throws Exception {
		Map map = MapUtils.put(null, null)
		                  .put("ANOTHER", "VALUE")
		                  .map();
		assertNotNull(map);
		assertEquals(2, map.size());
	}

}
