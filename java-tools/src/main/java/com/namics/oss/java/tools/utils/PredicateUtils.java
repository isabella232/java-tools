/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * PredicateUtil.
 *
 * @author aschaefer, Namics AG
 * @since 31.07.15 15:21  2.0
 */
public class PredicateUtils {

	/**
	 * Helper method to negate a predicate in a functional way.
	 *
	 * @param t   predicate to negate
	 * @param <T> Predicate evaluation type
	 * @return negated predicate
	 */
	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

	/**
	 * Helper method to perform null filtering.
	 *
	 * @param <T> Predicate evaluation type
	 * @return predicate that performs null check.
	 * @deprecated use {@link java.util.Objects::nonNull}
	 */
	@Deprecated
	public static <T> Predicate<T> notNull() {
		return x -> x != null;
	}

	/**
	 * Convert provided lambda to predicate instance to call methods on.
	 * e.g. <code>...stream().filter(as(test -> test != null).negate())...</code>
	 *
	 * @param predicate predicate to be wrapped
	 * @param <T>       the type of the input to the predicate
	 * @return predicate instance of lambda
	 */
	public static <T> Predicate<T> as(Predicate<T> predicate) {
		return predicate;
	}

	/**
	 * Convert provided lambda to consumer instance to call methods on.
	 *
	 * @param consumer consumer to be wrapped
	 * @param <T>      the type of the input to the consumer
	 * @return consumer instance of lambda
	 */
	public static <T> Consumer<T> as(Consumer<T> consumer) {
		return consumer;
	}

	/**
	 * Convert provided lambda to supplier instance to call methods on.
	 *
	 * @param supplier supplier to be wrapped
	 * @param <T>      the type of the input to the supplier
	 * @return supplier instance of lambda
	 */
	public static <T> Supplier<T> as(Supplier<T> supplier) {
		return supplier;
	}

}
