package com.namics.oss.java.tools.utils.test;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

public class StringLengthMatcher extends TypeSafeMatcher<CharSequence> {
	private final Matcher<? super Integer> lengthMatcher;

	/**
	 * Construct a new {@link StringLengthMatcher}.
	 *
	 * @param lengthMatcher matcher used to check the length
	 */
	public StringLengthMatcher(Matcher<? super Integer> lengthMatcher) {
		this.lengthMatcher = lengthMatcher;
	}

	@Factory
	public static StringLengthMatcher hasLength(Matcher<? super Integer> lengthMatcher) {
		return new StringLengthMatcher(lengthMatcher);
	}

	@Factory
	public static StringLengthMatcher hasLength(int length) {
		return hasLength(Matchers.equalTo(length));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean matchesSafely(CharSequence text) {
		return lengthMatcher.matches(text != null ? text.length() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("has string length ").appendDescriptionOf(lengthMatcher);
	}

}
