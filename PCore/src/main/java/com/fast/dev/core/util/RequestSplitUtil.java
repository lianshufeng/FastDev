package com.fast.dev.core.util;

import java.util.regex.Pattern;

/**
 * 请求数据分割工具
 *
 *
 * @作者 练书锋
 * @联系 251708339@qq.com
 */
public class RequestSplitUtil {
	/** Regex to parse HttpRequest Request Line */
	public static final Pattern REQUEST_LINE_PATTERN = Pattern.compile(" ");

	/** Regex to parse out QueryString from HttpRequest */
	public static final Pattern QUERY_STRING_PATTERN = Pattern.compile("\\?");

	/** Regex to parse out parameters from query string */
	public static final Pattern PARAM_STRING_PATTERN = Pattern.compile("\\&|;");

	/** Regex to parse out key/value pairs */
	public static final Pattern KEY_VALUE_PATTERN = Pattern.compile("=");

	/** Regex to parse raw headers and body */
	public static final Pattern RAW_VALUE_PATTERN = Pattern
			.compile("\\r\\n\\r\\n");

	/** Regex to parse raw headers from body */
	public static final Pattern HEADERS_BODY_PATTERN = Pattern
			.compile("\\r\\n");

	/** Regex to parse header name and value */
	public static final Pattern HEADER_VALUE_PATTERN = Pattern.compile(":");

	/** Regex to split cookie header following RFC6265 Section 5.4 */
	public static final Pattern COOKIE_SEPARATOR_PATTERN = Pattern.compile(";");
}
