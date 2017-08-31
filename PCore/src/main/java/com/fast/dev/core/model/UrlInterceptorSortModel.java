package com.fast.dev.core.model;

import java.util.Comparator;

import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * URL拦截器排序模型
 * 
 * @作者: 练书锋
 * @联系: 251708339@qq.com
 */
public class UrlInterceptorSortModel implements Comparator<UrlInterceptor> {

	@Override
	public int compare(UrlInterceptor urlInterceptor1, UrlInterceptor urlInterceptor2) {
		return urlInterceptor2.level() > urlInterceptor1.level() ? -1 : 1;
	}

}
