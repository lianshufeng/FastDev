package com.fast.dev.core.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * spring 容器自动注入，兼容原生态
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月13日
 *
 */
@SuppressWarnings("unchecked")
public abstract class NewInstanceHelper {

	@Autowired
	private ApplicationContext applicationContext;

	protected <T> T newInstance(Class<T> cls) {
		if (cls == null) {
			return null;
		}
		Object instance = null;
		if (applicationContext != null) {
			instance = applicationContext.getBean(cls);
		} else {
			try {
				instance = cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T) instance;
	}

}
