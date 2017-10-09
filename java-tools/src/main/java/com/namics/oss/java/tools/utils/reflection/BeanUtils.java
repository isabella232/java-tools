/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * BeanUtils.
 *
 * @author aschaefer
 * @since 21.02.14 09:55
 */
public class BeanUtils {

	public static List<PropertyDescriptor> getPropertyDescriptors(Class<?> beanClass) {
		try {
			BeanInfo beanInfo = new ExtendedBeanInfo(Introspector.getBeanInfo(beanClass));

			// This call is slow so we do it once.
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			return Arrays.asList(pds);
		} catch (IntrospectionException e) {
			return Collections.emptyList();
		}
	}

	private static final List<String> ALWAYS_IGNORE_PROPERTIES = Arrays.asList("class");

	/**
	 * Copy the property values of the given source bean into the given target map.
	 *
	 * @param source           the source bean
	 * @param ignoreProperties Vararg of property names to ignore, "class" is excluded per default if non provided.
	 * @return a map containing all bean properties of the source object as map entries.
	 */
	public static Map<String, Object> getPropertiesMap(Object source,
	                                                   String... ignoreProperties) {
		return getPropertiesMap(source, Object.class, ignoreProperties);
	}

	/**
	 * Copy the property values of the given source bean into the given target map. This method can only copy values, that are assignable to Type
	 * clazz, for unassignable objects null will be added to the map.
	 *
	 * @param source           the source bean
	 * @param clazz            Type of return Map's value
	 * @param ignoreProperties Vararg of property names to ignore, "class" is excluded per default if non provided.
	 * @return a map containing all bean properties of the source object as map entries.
	 */
	public static <T> Map<String, T> getPropertiesMap(Object source,
	                                                  Class<T> clazz,
	                                                  String... ignoreProperties) {

		Map<String, T> target = new HashMap<String, T>();
		if (source == null) {
			return target;
		}

		Class<?> actualEditable = source.getClass();

		List<PropertyDescriptor> sourcePds = getPropertyDescriptors(actualEditable);

		List<String> ignoreList = new ArrayList<>();
		ignoreList.addAll(ALWAYS_IGNORE_PROPERTIES);
		ignoreList.addAll(Arrays.asList(ignoreProperties));

		for (PropertyDescriptor sourcePd : sourcePds) {
			if (sourcePd.getReadMethod() != null && !ignoreList.contains(sourcePd.getName())) {
				try {
					Method readMethod = sourcePd.getReadMethod();
					if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
						readMethod.setAccessible(true);
					}
					Object value = readMethod.invoke(source);
					if (value != null && clazz.isAssignableFrom(value.getClass())) {
						target.put(sourcePd.getName(), (T) value);
					} else {
						target.put(sourcePd.getName(), null);
					}
				} catch (Exception ex) {
					throw new RuntimeException("Could not copy properties from source to target", ex);
				}
			}
		}
		return target;
	}
}
