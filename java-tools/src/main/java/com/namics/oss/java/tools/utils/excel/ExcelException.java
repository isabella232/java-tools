/*
 * Copyright 2000-2015 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.excel;

/**
 * ExcelException.
 *
 * @author aschaefer, Namics AG
 * @since 21.07.15 09:32
 */
public class ExcelException extends RuntimeException {
	public ExcelException() {
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

	public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
