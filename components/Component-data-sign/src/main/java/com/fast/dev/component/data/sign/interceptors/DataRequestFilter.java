package com.fast.dev.component.data.sign.interceptors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.fast.dev.component.data.sign.request.CacheRequestWapper;

/**
 * Servlet Filter implementation class DataSe
 */
@WebFilter(urlPatterns = "/*")
@Component
public class DataRequestFilter implements Filter {

	private static MappedInterceptor mappedInterceptor;
	private static AntPathMatcher antPathMatcher = new AntPathMatcher();

	// 因为会创建两个实力，所以只能用静态变量的方式注入
	@Autowired
	private void initBean(ApplicationContext applicationContext) {
		// 初始化字典拦截器
		DataSignInterceptor dataSignInterceptor = applicationContext.getBean(DataSignInterceptor.class);
		DataRequestFilter.mappedInterceptor = new MappedInterceptor(dataSignInterceptor.addPathPatterns(),
				dataSignInterceptor.excludePathPatterns(), dataSignInterceptor);
	}

	/**
	 * Default constructor.
	 */
	public DataRequestFilter() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(wapperRequest(request), response);
	}

	/**
	 * 根绝拦截器的配置规则封装request
	 * 
	 * @return
	 * @throws Exception
	 */
	private ServletRequest wapperRequest(ServletRequest request) throws IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String lookupPath = httpServletRequest.getServletPath();
		if (mappedInterceptor != null && mappedInterceptor.matches(lookupPath, antPathMatcher)) {
			return new CacheRequestWapper((HttpServletRequest) request);
		}
		return request;
	}

	@Override
	public void destroy() {

	}

}
