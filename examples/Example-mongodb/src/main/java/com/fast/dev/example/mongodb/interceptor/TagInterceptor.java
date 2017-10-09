package com.fast.dev.example.mongodb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.interceptor.UrlInterceptor;

@Component
public class TagInterceptor implements UrlInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		modelAndView.getModel().put("tag", "Example-mongodb");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] addPathPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] excludePathPatterns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		return 0;
	}

}
