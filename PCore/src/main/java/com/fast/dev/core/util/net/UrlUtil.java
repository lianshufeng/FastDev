package com.fast.dev.core.util.net;

import java.net.URISyntaxException;

/**
 * URI的常用工具
 * 
 * @author 练书锋
 *
 */
public class UrlUtil {

	/**
	 * 取出URL中的最后资源名 如 /xx/xx/11.html ,取出为11.html
	 * 
	 * @param url
	 * @return
	 */
	public static String getSimpleUri(final String url) {
		int last = url.lastIndexOf("/");
		return last != -1 ? url.substring(last + 1, url.length()) : url;
	}

	/**
	 * 格式化URL
	 * 
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 */
	public static String format(final String url) {
		String uri = url;
		int schemeAt = uri.indexOf("://");
		String scheme = schemeAt == -1 ? null : uri.substring(0, schemeAt);
		if (scheme != null) {
			uri = uri.substring(schemeAt + 3, uri.length());
		}
		uri = uri.replaceAll("\\\\", "/");
		while (uri.indexOf("//") != -1) {
			uri = uri.replaceAll("//", "/");
		}
		if (scheme != null) {
			uri = scheme + "://" + uri;
		}
		return uri;
	}

}
