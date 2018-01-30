/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.junit.Test;

import static com.namics.oss.java.tools.utils.test.PropertyMatcher.hasPropertiesOf;
import static org.junit.Assert.assertThat;

/**
 * PropertyMatcherTest.
 *
 * @author aschaefer, Namics AG
 * @since 10.09.15 11:21
 */
public class PropertyMatcherTest {


	@Test (expected = AssertionError.class)
	public void testNull() throws Exception {
		assertThat(null, hasPropertiesOf(null));
	}

	@Test
	public void testOk() throws Exception {
		assertThat(new A().bool(true).stringA("A").stringB("B"), hasPropertiesOf(new B().bool(true).stringA("A").stringC("C"), "stringB"));
	}

	@Test(expected = AssertionError.class)
//	@Test
	public void testMissing() throws Exception {
		assertThat(new A().bool(true).stringB("B"), hasPropertiesOf(new B().bool(true).stringA("A").stringC("C"), "stringB"));
	}

	public static class A {
		private String stringA;
		private String stringB;
		private boolean bool;

		public String getStringA() {
			return stringA;
		}

		public void setStringA(String stringA) {
			this.stringA = stringA;
		}

		public String getStringB() {
			return stringB;
		}

		public void setStringB(String stringB) {
			this.stringB = stringB;
		}

		public boolean isBool() {
			return bool;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}


		public A stringA(String stringA) {
			setStringA(stringA);
			return this;
		}

		public A stringB(String stringB) {
			setStringB(stringB);
			return this;
		}

		public A bool(boolean bool) {
			setBool(bool);
			return this;
		}
	}

	public static class B {
		private String stringA;
		private String stringC;
		private boolean bool;

		public String getStringA() {
			return stringA;
		}

		public void setStringA(String stringA) {
			this.stringA = stringA;
		}

		public String getStringC() {
			return stringC;
		}

		public void setStringC(String stringC) {
			this.stringC = stringC;
		}

		public boolean isBool() {
			return bool;
		}

		public void setBool(boolean bool) {
			this.bool = bool;
		}


		public B stringA(String stringA) {
			setStringA(stringA);
			return this;
		}

		public B stringC(String stringC) {
			setStringC(stringC);
			return this;
		}

		public B bool(boolean bool) {
			setBool(bool);
			return this;
		}
	}

}