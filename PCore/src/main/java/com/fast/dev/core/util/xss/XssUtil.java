package com.fast.dev.core.util.xss;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fast.dev.core.util.xss.wraps.XssWarpClean;
import com.fast.dev.core.util.xss.wraps.impl.ArrayXssWarpClean;
import com.fast.dev.core.util.xss.wraps.impl.ListXssWarpClean;
import com.fast.dev.core.util.xss.wraps.impl.MapXssWarpClean;
import com.fast.dev.core.util.xss.wraps.impl.ObjectXssWarpClean;
import com.fast.dev.core.util.xss.wraps.impl.SetXssWarpClean;

/**
 * Xss过滤对象
 * 
 * @作者 练书锋
 * @时间 2017年6月9日
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XssUtil {

	// 跳过不处理的类型
	private final static List<Class<?>> SkipClasses = new ArrayList<Class<?>>() {

		private static final long serialVersionUID = 1L;

		{
			add(Boolean.TYPE);
			add(Boolean.class);
			add(Byte.TYPE);
			add(Byte.class);
			add(Character.TYPE);
			add(Character.class);
			add(Short.TYPE);
			add(Short.class);
			add(Integer.TYPE);
			add(Integer.class);
			add(Long.TYPE);
			add(Long.class);
			add(Double.TYPE);
			add(Double.class);
			add(Float.TYPE);
			add(Float.class);
			add(Void.TYPE);
			add(Void.class);
			add(null);
		}
	};

	// xss对象处理
	private final static Map<Class<?>, XssWarpClean> XssWarpCleanMap = new HashMap<Class<?>, XssWarpClean>() {
		private static final long serialVersionUID = 1L;

		{
			put(Array.class, new ArrayXssWarpClean());
			put(Set.class, new SetXssWarpClean());
			put(List.class, new ListXssWarpClean());
			put(Map.class, new MapXssWarpClean());
			put(Object.class, new ObjectXssWarpClean());
		}
	};

	/**
	 * 清洗非法数据
	 * 
	 * @param o
	 * @return
	 */
	public static <T> T wrapper(Object o) {
		if (o == null) {
			return null;
		}
		// 过滤字符串
		if (o instanceof String) {
			return (T) cleanXSS(String.valueOf(o));
		}
		// 原样返回
		if (SkipClasses.contains(o.getClass())) {
			return (T) o;
		}
		Class<?> targetCls = null;
		// 处理类型类型
		if (o instanceof Map) {
			targetCls = Map.class;
		} else if (o instanceof List) {
			targetCls = List.class;
		} else if (o instanceof Set) {
			targetCls = Set.class;
		} else if (o.getClass().isArray()) {
			targetCls = Array.class;
		} else {
			targetCls = Object.class;
		}
		return (T) XssWarpCleanMap.get(targetCls).warp(o);
	}

	/**
	 * 是否封装对象
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isWrapClass(Object o) {
		return !SkipClasses.contains(o.getClass());
	}

	/**
	 * 清除xss威胁
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) {
		value = value.replaceAll(";", "；");
		value = value.replaceAll("\\\\", "").replaceAll("\\\\/", "");
		value = value.replaceAll("\\(", "（").replaceAll("\\)", "）");
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		//HTML的特殊符号编码
		value = StringEscapeUtils.escapeHtml4(value);
		return value;
	}

}
