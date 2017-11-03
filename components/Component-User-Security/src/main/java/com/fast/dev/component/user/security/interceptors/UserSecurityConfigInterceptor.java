package com.fast.dev.component.user.security.interceptors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.component.user.security.model.UserSecurityConfig;
import com.fast.dev.component.user.security.service.impl.UserSecurityAuthenticationManager;
import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * 权限需要访问方法的拦截器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
public class UserSecurityConfigInterceptor implements UrlInterceptor {

	private UserSecurityConfig userSecurityConfig;

	public UserSecurityConfigInterceptor() {
	}

	public UserSecurityConfigInterceptor(UserSecurityConfig userSecurityConfig) {
		super();
		this.userSecurityConfig = userSecurityConfig;
	}

	@Resource
	private UserSecurityAuthenticationManager userSecurityAuthenticationManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		userSecurityAuthenticationManager.authentication(request);
		return true;
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
		return userSecurityConfig.getNeedSecurityMethodUrl();
	}

	@Override
	public String[] excludePathPatterns() {
		return userSecurityConfig.getExcludeSecurityMethodUrl();
	}

	@Override
	public int level() {
		return userSecurityConfig.getSecurityMethodUrlLevel();
	}

}
