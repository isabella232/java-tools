/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TimeoutHelper.
 *
 * @author aschaefer, Namics AG
 * @since 21.08.15 11:26
 */
public abstract class TimeoutHelper {
	private static final Logger LOG = LoggerFactory.getLogger(TimeoutHelper.class);

	/**
	 * Silent sleep current thread.
	 *
	 * @param millis tiome to wait in ms.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			LOG.debug("sleep for {} ms failed: {}", millis, e, null);
		}
	}
}
