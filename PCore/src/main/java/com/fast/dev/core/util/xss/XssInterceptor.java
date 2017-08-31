package com.fast.dev.core.util.xss;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * Xss 的拦截器，直接配置到spring容器即可,默认不启用
 * 
 * @作者 练书锋
 * @时间 2017年6月11日
 *
 */
public class XssInterceptor implements UrlInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 过滤model对象
		Map<String, Object> model = modelAndView.getModel();
		for (String key : model.keySet()) {
			model.put(key, XssUtil.wrapper(model.get(key)));
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public String[] addPathPatterns() {
		return new String[] { "/**/*" };
	}

	@Override
	public String[] excludePathPatterns() {
		return new String[] { "/**/*.json", "/**/*.xml" };
	}

	@Override
	public int level() {
		return 0;
	}

}
