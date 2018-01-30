/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import com.namics.oss.java.tools.utils.reflection.BeanUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * PropertyMatcher.
 *
 * @author aschaefer, Namics AG
 * @since 10.09.15 10:38
 */
public class PropertyMatcher extends TypeSafeMatcher<Object> {

	private final Object reference;
	private String[] ignores ={"class"};
	private List<String> ignoreList = new ArrayList<>();
	{
		ignoreList.add("class");
	}
	private final Map<String, String> report = new HashMap<>();

	public PropertyMatcher(final Object reference) {
		this.reference = reference;
	}


	@Override
	public void describeTo(final Description description) {
		description.appendText("Properties matching those of " + reference);

	}

	@Override
	protected void describeMismatchSafely(Object item, Description mismatchDescription) {
		for (Map.Entry<String, String> result : report.entrySet()) {
			mismatchDescription.appendText("\n\t\t" + result.getKey() + "\t : " + result.getValue());
		}
	}


	@Override
	public boolean matchesSafely(final Object candidate) {
		boolean ok = true;
		Map<String, Object> references = BeanUtils.getPropertiesMap(reference, ignores);
		Map<String, Object> actuals = BeanUtils.getPropertiesMap(candidate, ignores);

		List<PropertyDescriptor> descriptors = BeanUtils.getPropertyDescriptors(candidate.getClass());
		for (PropertyDescriptor descriptor : descriptors) {
			if (!ignoreList.contains(descriptor.getName()) && !actuals.containsKey(descriptor.getName())) {
				actuals.put(descriptor.getName(), null);
			}
		}

		for (Map.Entry<String, Object> actual : actuals.entrySet()) {
			Object reference = references.get(actual.getKey());
			if (reference == null) {
				report.put("===>\t" + actual.getKey(), "not found, consider ignore");
				ok = false;
			} else {
				if (!reference.equals(actual.getValue())) {
					report.put("===>\t" + actual.getKey(), actual.getValue() + " != " + reference);
					ok = false;
				} else {
					report.put("    \t" + actual.getKey(), actual.getValue() + " == " + reference);
				}
			}
		}
		return ok;
	}

	/**
	 * Matcher to check if all properties of the tested object are contained in the reference object.
	 *
	 * @param reference reference expression.
	 * @return Matcher for assertions
	 */
	public static PropertyMatcher hasPropertiesOf(final Object reference) {
		return new PropertyMatcher(reference);
	}

	/**
	 * Matcher to check if all properties of the tested object are contained in the reference object.
	 *
	 * @param reference reference expression.
	 * @param ignores   ignore this properties
	 * @return Matcher for assertions
	 */
	public static PropertyMatcher hasPropertiesOf(final Object reference, String... ignores) {
		return new PropertyMatcher(reference).ignores(ignores);
	}


	public void setIgnores(String... ignores) {
		this.ignoreList = new ArrayList<>();
		this.ignoreList.add("class");
		if (ignores != null) {
			this.ignoreList.addAll(asList(ignores));
		}
		this.ignores = this.ignoreList.toArray(new String[this.ignoreList.size()]);
	}

	public PropertyMatcher ignores(String... ignores) {
		setIgnores(ignores);
		return this;
	}
}
