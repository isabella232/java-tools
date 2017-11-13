/*
 * Copyright 2000-2016 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * ArrayUtils wrapper methods for common use case of null save 1:1 copy of arrays.
 * Overloading of several #copyOf is required for performance reasons.
 *
 * @author aschaefer, Namics AG
 * @see Arrays
 * @since 05.02.16 08:01, 2.0
 */
public class ArrayUtils {

	/**
	 * easy array init with vararg, reduces need for new Type[]{} initialization.
	 *
	 * @param input vararg
	 * @param <T>   type of array items
	 * @return array from vararg
	 */
	@SafeVarargs
	public static <T> T[] array(T... input) {
		return input;
	}

	/**
	 * Returns a sequential {@link Stream} with the specified array as its
	 * source.
	 *
	 * @param <T>   The type of the array elements
	 * @param array The array, assumed to be unmodified during use
	 * @return a {@code Stream} for the array
	 * @since 2.0
	 */
	@SafeVarargs
	public static <T> Stream<T> stream(T... array) {
		return Arrays.stream(array);
	}


	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param <T>      the class of the objects in the array
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] copyOf(T[] original) {
		if (original == null) {
			return null;
		}
		return (T[]) Arrays.copyOf(original, original.length);
	}


	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static byte[] copyOf(byte[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static short[] copyOf(short[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static int[] copyOf(int[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static long[] copyOf(long[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static char[] copyOf(char[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static float[] copyOf(float[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static double[] copyOf(double[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Copies the specified array as is.
	 * so the copy has the specified length.  For all indices that are
	 * valid in both the original array and the copy, the two arrays will
	 * contain identical values.
	 * The resulting array is of exactly the same class as the original array.
	 *
	 * @param original the array to be copied, may be null
	 * @return a copy of the original array, null for null original
	 * @since 2.0
	 */
	public static boolean[] copyOf(boolean[] original) {
		if (original == null) {
			return null;
		}
		return Arrays.copyOf(original, original.length);
	}

	/**
	 * Utility to join an existing array with further items to a combined array.
	 *
	 * @param base base array to join with
	 * @param join items to join to the array
	 * @param <T>  type of items
	 * @return joined array, new instance guaranteed
	 */
	public static <T> T[] join(T[] base, T... join) {
		if (base == null) {
			return copyOf(join);
		}
		if (join == null) {
			return copyOf(base);
		}
		int newLength = base.length + join.length;
		Class<?> newType =  base.getClass();

		@SuppressWarnings("unchecked")
		T[] result = ((Object)newType == (Object)Object[].class)
				? (T[]) new Object[newLength]
				: (T[]) Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(base, 0, result, 0, base.length);
		System.arraycopy(join, 0, result, base.length, join.length);
		return result;
	}
}
