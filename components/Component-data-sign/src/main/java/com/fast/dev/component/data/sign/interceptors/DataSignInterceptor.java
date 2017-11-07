package com.fast.dev.component.data.sign.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.component.data.sign.model.DataSignConfig;
import com.fast.dev.component.data.sign.service.impl.DataValidateService;
import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * URL 拦截器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月5日
 *
 */
public class DataSignInterceptor implements UrlInterceptor {

	@Resource
	private DataValidateService dataValidateService;

	private DataSignConfig dataSignConfig;

	public DataSignInterceptor() {
		// TODO Auto-generated constructor stub
	}

	public DataSignInterceptor(DataSignConfig dataSignConfig) {
		super();
		this.dataSignConfig = dataSignConfig;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return dataValidateService.validateData(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public String[] addPathPatterns() {
		return dataSignConfig.getUrl().getAddPathPatterns();
	}

	@Override
	public String[] excludePathPatterns() {
		return dataSignConfig.getUrl().getExcludePathPatterns();
	}

	@Override
	public int level() {
		return dataSignConfig.getUrl().getLevel();
	}

}
