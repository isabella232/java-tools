/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.test;

import org.junit.jupiter.api.Test;

import static com.namics.oss.java.tools.utils.test.PropertyMatcher.hasPropertiesOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * PropertyMatcherTest.
 *
 * @author aschaefer, Namics AG
 * @since 10.09.15 11:21
 */
class PropertyMatcherTest {


	@Test
	void testNull() throws Exception {
		assertThrows(AssertionError.class,() -> assertThat(null, hasPropertiesOf(null)));
	}

	@Test
	void testOk() throws Exception {
		assertThat(new A().bool(true).stringA("A").stringB("B"), hasPropertiesOf(new B().bool(true).stringA("A").stringC("C"), "stringB"));
	}

	@Test
	void testMissing() throws Exception {
		assertThat(new A().bool(true).stringB("B"), hasPropertiesOf(new B().bool(true).stringA("A").stringC("C"), "stringB"));
	}

	static class A {
		private String stringA;
		private String stringB;
		private boolean bool;

		String getStringA() {
			return stringA;
		}

		void setStringA(String stringA) {
			this.stringA = stringA;
		}

		String getStringB() {
			return stringB;
		}

		void setStringB(String stringB) {
			this.stringB = stringB;
		}

		boolean isBool() {
			return bool;
		}

		void setBool(boolean bool) {
			this.bool = bool;
		}


		A stringA(String stringA) {
			setStringA(stringA);
			return this;
		}

		A stringB(String stringB) {
			setStringB(stringB);
			return this;
		}

		A bool(boolean bool) {
			setBool(bool);
			return this;
		}
	}

	static class B {
		private String stringA;
		private String stringC;
		private boolean bool;

		String getStringA() {
			return stringA;
		}

		void setStringA(String stringA) {
			this.stringA = stringA;
		}

		String getStringC() {
			return stringC;
		}

		void setStringC(String stringC) {
			this.stringC = stringC;
		}

		boolean isBool() {
			return bool;
		}

		void setBool(boolean bool) {
			this.bool = bool;
		}


		B stringA(String stringA) {
			setStringA(stringA);
			return this;
		}

		B stringC(String stringC) {
			setStringC(stringC);
			return this;
		}

		B bool(boolean bool) {
			setBool(bool);
			return this;
		}
	}

}
