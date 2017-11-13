/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * SimpleMap.
 *
 * @param <KEY>   serializable map key type
 * @param <VALUE> serializable map value type
 * @author aschaefer, Namics AG
 * @since 07.05.14 13:27
 * @deprecated Use {@link MapBuilder} or {@link MapUtils#put(Object, Object)} instead.
 */
@Deprecated
public final class SimpleMap<KEY extends Serializable, VALUE extends Serializable> extends TreeMap<KEY, VALUE> {
	private static final long serialVersionUID = 30000L;

	/**
	 * {@inheritDoc}
	 */
	public SimpleMap() {
	}

	/**
	 * {@inheritDoc}
	 */
	public SimpleMap(Map<? extends KEY, ? extends VALUE> m) {
		super(m);
	}

	/**
	 * put a value for the key and return this map for fluent programming style.
	 *
	 * @param key   key to add a value for
	 * @param value value to add for key
	 * @return this map instance
	 */
	public SimpleMap<KEY, VALUE> add(KEY key, VALUE value) {
		this.put(key, value);
		return this;
	}

	/**
	 * Create a new map instance and populate with the key value pair.
	 *
	 * @param key     key to add to new map
	 * @param value   value to add to new map
	 * @param <KEY>   serializable map key type
	 * @param <VALUE> serializable map value type>
	 * @return
	 */
	public static <KEY extends Serializable, VALUE extends Serializable> SimpleMap<KEY, VALUE> asMap(KEY key, VALUE value) {
		return new SimpleMap<KEY, VALUE>().add(key, value);
	}
}
