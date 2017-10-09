/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

/**
 * Helper utils for map handling.
 *
 * @author aschaefer, Namics AG
 * @since 13.06.14 10:07
 */
public class MapUtils {

	/**
	 * Util not to be instantiated.
	 */
	private MapUtils() {
		throw new UnsupportedOperationException("MapUtils cannot be instantiated");
	}

	/**
	 * Start to build a new map with a fresh instance of MapBuilder.
	 *
	 * @param key     Key of value to put in a map build with the fresh builder instance
	 * @param value   Value to be put in a map build with the fresh builder instance
	 * @param <KEY>   Type of the key
	 * @param <VALUE> Type of the value
	 * @return MapBuilder instance to proceed in building the map.
	 */
	public static <KEY, VALUE> MapBuilder<KEY, VALUE> put(KEY key, VALUE value) {
		return new MapBuilder<KEY, VALUE>().put(key, value);
	}
}
