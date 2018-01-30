/*
* Copyright 2000-2015 Namics AG. All rights reserved.
*/

package com.namics.oss.java.tools.utils.test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

/**
 * Hamcrest Matcher for String verification with Regex.
 * ${NAME}.
 *
 * @author aschaefer, Namics AG
 * @since 23.03.2015 09:56
 */
public class RegexMatcher extends TypeSafeMatcher<String> {

	private final Pattern pattern;

	public RegexMatcher(final String regex) {
		this(Pattern.compile(regex));
	}

	public RegexMatcher(final Pattern pattern) {
		this.pattern = pattern;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendText("matches regex=`" + pattern + "`");
	}

	@Override
	public boolean matchesSafely(final String string) {
		return pattern.matcher(string).matches();
	}

	/**
	 * Matcher to match a String against a regex expression.
	 *
	 * @param regex regex expression.
	 * @return Matcher for assertions
	 */
	public static RegexMatcher matchesRegex(final String regex) {
		return new RegexMatcher(regex);
	}

	/**
	 * Matcher to match a String against a regex pattern.
	 *
	 * @param pattern compiled regex pattern.
	 * @return Matcher for assertions
	 */
	public static RegexMatcher matchesRegex(final Pattern pattern) {
		return new RegexMatcher(pattern);
	}
}