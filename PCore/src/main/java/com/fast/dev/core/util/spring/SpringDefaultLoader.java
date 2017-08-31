package com.fast.dev.core.util.spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * spring默认对象装载器
 * 
 * @作者: 练书锋
 * @联系: 251708339@qq.com
 */
public class SpringDefaultLoader {

	/**
	 * 如果该接口有多个子类，则返回除默认接口的类，如果没有子类则返回默认类
	 * 
	 * @param applicationContext
	 * @param cls
	 * @param defaultName
	 * @return
	 */
	public static <T> T load(final ApplicationContext applicationContext,
			final Class<T> cls, final String defaultName) {
		T t = null;
		Map<String, T> m = applicationContext.getBeansOfType(cls);
		if (m.size() == 1) {
			t = m.get(defaultName);
		} else if (m.size() > 1) {
			m.remove(defaultName);
			t = m.values().iterator().next();
		}
		return t;
	}

}
