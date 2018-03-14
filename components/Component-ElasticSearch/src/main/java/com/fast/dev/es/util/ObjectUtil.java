package com.fast.dev.es.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 对象工具
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */
public class ObjectUtil {

	/**
	 * 转换到map
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> toMap(Object obj) {
		try {
			return PropertyUtils.describe(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
