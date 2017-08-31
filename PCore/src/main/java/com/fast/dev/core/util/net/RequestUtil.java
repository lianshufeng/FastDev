package com.fast.dev.core.util.net;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	/**
	 * 取出请求的URL
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {

		return request.getScheme() + "://" + request.getServerName()
				+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath();
	}

}
