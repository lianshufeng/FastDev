package com.fast.dev.core.cast.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.cast.container.TimeCastThreadContainer;
import com.fast.dev.core.cast.model.TimeCastModel;
import com.fast.dev.core.cast.service.TimeCastService;
import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * 耗时拦截器
 * 
 * @author Administrator
 *
 */
@Component
public class UsedTimeUrlInterceptor implements UrlInterceptor {

	@Autowired
	private TimeCastThreadContainer timeCastThreadContainer;

	@Autowired
	private TimeCastService timeCastService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		TimeCastModel timeCastModel = new TimeCastModel();
		timeCastModel.setAccessTime(System.currentTimeMillis());
		this.timeCastThreadContainer.set(timeCastModel);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		this.timeCastService.setCastTime(modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		this.timeCastThreadContainer.remove();
	}

	@Override
	public String[] addPathPatterns() {
		return new String[] { "/**/*.json" };
	}

	@Override
	public String[] excludePathPatterns() {
		return null;
	}

	@Override
	public int level() {
		return Integer.MIN_VALUE;
	}

}
