/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.maps;

import java.util.*;

/**
 * Helper to bu@ild maps with builder Pattern.
 *
 * @param <KEY>   Type of keys to be used in map to be build
 * @param <VALUE> Type of values to be used in map to be build
 * @author aschaefer, Namics AG
 * @since 13.06.14 10:40
 */
public class MapBuilder<KEY, VALUE> {
	protected Map<KEY, VALUE> target;

	/**
	 * Create a builder with a default map implementation.
	 */
	public MapBuilder() {
		this.target = new HashMap<KEY, VALUE>();
	}

	/**
	 * Create a builder to populate an existing Map instances.
	 * The provided map will be modified!
	 *
	 * @param target Map Instance to be populated.
	 */
	public MapBuilder(Map<KEY, VALUE> target) {
		this.target = target;
	}

	/**
	 * Create a builder to create a map of the provided type.
	 *
	 * @param clazz Type of Map to be created with this builder.
	 * @param <MAP> Type of map to be build with this builder.
	 * @throws IllegalArgumentException    if the class or its nullary
	 *                                     constructor is not accessible.
	 * @throws IllegalArgumentException    if this {@code Class} represents an abstract class,
	 *                                     an interface, an array class, a primitive type, or void;
	 *                                     or if the class has no nullary constructor;
	 *                                     or if the instantiation fails for some other reason.
	 * @throws ExceptionInInitializerError if the initialization
	 *                                     provoked by this method fails.
	 * @throws SecurityException           If a security manager, <i>s</i>, is present and any of the
	 *                                     following conditions is met:
	 *                                     <ul>
	 *                                     <li> invocation of
	 *                                     {@link SecurityManager#checkMemberAccess
	 *                                     s.checkMemberAccess(this, Member.PUBLIC)} denies
	 *                                     creation of new instances of this class
	 *                                     <li> the caller's class loader is not the same as or an
	 *                                     ancestor of the class loader for the current class and
	 *                                     invocation of {@link SecurityManager#checkPackageAccess
	 *                                     s.checkPackageAccess()} denies access to the package
	 *                                     of this class
	 *                                     </ul>
	 */
	public <MAP extends Map<KEY, VALUE>> MapBuilder(Class<MAP> clazz) {
		try {
			this.target = clazz.newInstance();
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Put key value pair into map to be build
	 *
	 * @param key   key to use for pair
	 * @param value value for key
	 * @return this builder for fluent programming
	 */
	public MapBuilder<KEY, VALUE> put(KEY key, VALUE value) {
		this.target.put(key, value);
		return this;
	}

	/**
	 * Get the map build with this builder.
	 *
	 * @return current state of map build with this builder.
	 */
	public Map<KEY, VALUE> map() {
		return this.target;
	}

	/**
	 * Get an immutable instance of the current map.
	 *
	 * @return map that cannot be modified anymore.
	 */
	public Map<KEY, VALUE> immutable() {

		Map<KEY, VALUE> current;
		try {
			current = this.target.getClass().newInstance();
		} catch (Throwable e) {
			current = new HashMap<KEY, VALUE>();
		}
		current.putAll(this.target);
		return Collections.unmodifiableMap(current);
	}

	public SortedMap<KEY, VALUE> sorted() {
		if (this.target instanceof SortedMap) {
			return (SortedMap) this.target;
		}
		TreeMap<KEY, VALUE> sorted = new TreeMap<KEY, VALUE>();
		sorted.putAll(this.target);
		return sorted;
	}
}
