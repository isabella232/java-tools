/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.properties;

import java.util.Properties;

/**
 * PropertiesBuilder.
 *
 * @author aschaefer, Namics AG
 * @since 27.08.15 14:04
 */
public class PropertiesBuilder {

	protected final Properties target;

	/**
	 * Create a new PropetiesBuilder, this Object is stateful, single usage only!
	 */
	public PropertiesBuilder() {
		this(null);
	}

	/**
	 * Create a new PropetiesBuilder on an existing Properties instance, this Object is stateful, single usage only!
	 *
	 * @param target Properties instance to be used with this builder.
	 */
	public PropertiesBuilder(Properties target) {
		this.target = target != null ? target : new Properties();
	}

	/**
	 * Put key value pair into the properties obejct under construction.
	 *
	 * @param key   key to use for pair
	 * @param value value for key
	 * @return this builder for fluent programming
	 */
	public PropertiesBuilder put(String key, String value) {
		this.target.setProperty(key, value);
		return this;
	}

	/**
	 * get the actual propeties object.
	 *
	 * @return build instance of Properties.
	 */
	public Properties properties() {
		return target;
	}
}
