/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * MapCreatorTest.
 *
 * @author aschaefer, Namics AG
 * @since 14.12.16 08:33
 */
class MapCreatorTest {
	private static final Logger LOG = LoggerFactory.getLogger(MapCreatorTest.class);

	@Test
	void simpleDemo() throws Exception {
		Map<String, Integer> ints = MapCreator.mapOf(
				one -> 1,
				two -> 2
		);

		LOG.info("{}", ints);
		Map<String, String> strings = MapCreator.mapOf(
				one -> "eins",
				two -> "zwei"
		);
		LOG.info("{}", strings);
	}

	@Test
	void hierarchyDemo() throws Exception {
		Map<String, Map<String, Integer>> inhabitants = MapCreator.mapOf(
				usa -> MapCreator.mapOf(
						new_york -> 8_550_405,
						los_angeles -> 3_971_883,
						chicago -> 2_720_546
				),
				canada -> MapCreator.mapOf(
						toronto -> 2_615_060,
						montreal -> 1_649_519,
						calgary -> 1_096_833
				)
		);
		LOG.info("{}", inhabitants);

	}
}
