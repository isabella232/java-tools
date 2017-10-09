/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.properties;

/**
 * PropertiesUtils.
 *
 * @author aschaefer, Namics AG
 * @since 27.08.15 14:08
 */
public abstract class PropertiesUtils {

	private PropertiesUtils() {
		throw new UnsupportedOperationException("PropertiesUtils cannot be instantiated");
	}

	/**
	 * Create PropertiesBuilder and put a value in it, all PropertiesBuilder#properties to get actual Properties.
	 *
	 * @param key   property key to put
	 * @param value property value to put
	 * @return PropertiesBuilder instance with provided property, call PropertiesBuilder#properties for actual Properties.
	 */
	public static PropertiesBuilder put(String key, String value) {
		return new PropertiesBuilder().put(key, value);
	}
}
