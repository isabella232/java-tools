/*
 * Copyright 2000-2014 Namics AG. All rights reserved.
 */

package com.namics.oss.java.tools.utils.web;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestUtils.
 *
 * @author aschaefer, Namics AG
 * @since 10.09.14 14:09
 */
public class RequestUtils {
	/**
	 * get the base url of the current application.
	 *
	 * @param request request
	 * @return url string like http://my.server.com/myapp
	 */
	public static String getBaseUrl(HttpServletRequest request) {
		StringBuilder builder = new StringBuilder();
		builder
				.append(request.getScheme())
				.append("://")
				.append(request.getServerName());
		if (request.getServerPort() != 80
				&& request.getServerPort() != 443) {
			builder
					.append(":")
					.append(request.getServerPort());
		}
		String contextPath = request.getContextPath();
		if (contextPath != null && contextPath.length() > 0){
			builder.append(contextPath);
		}

		return builder.toString();
	}
}
