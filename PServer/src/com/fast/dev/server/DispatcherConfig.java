package com.fast.dev.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import com.fast.dev.core.charset.DefaultCharSet;
import com.fast.dev.core.interceptor.UrlInterceptor;
import com.fast.dev.core.model.UrlInterceptorSortModel;

@Configuration
@SuppressWarnings({ "unchecked" })
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver contentView = new ContentNegotiatingViewResolver();
		// 添加视图解析器
		List<ViewResolver> viewResolverList = new ArrayList<ViewResolver>(
				this.applicationContext.getBeansOfType(ViewResolver.class).values());

		contentView.setViewResolvers(viewResolverList);
		// 设置默认视图
		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(jsonView());
		defaultViews.add(xmlView());
		contentView.setDefaultViews(defaultViews);
		return contentView;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages");
		return resource;
	}

	/**
	 * json 视图解析器
	 * 
	 * @return
	 */
	@Bean
	public View jsonView() {
		return new MappingJackson2JsonView();
	}

	/**
	 * xml视图解析器
	 * 
	 * @return
	 */
	@Bean
	public View xmlView() {
		return new MappingJackson2XmlView();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
		registry.addViewController("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		Map<String, UrlInterceptor> m = this.applicationContext.getBeansOfType(UrlInterceptor.class);
		// 存储接口实现对应的spring的bean名称
		Map<UrlInterceptor, String> interceptorCache = new HashMap<UrlInterceptor, String>();
		for (String key : m.keySet()) {
			interceptorCache.put(m.get(key), key);
		}
		// 优先级排序
		List<UrlInterceptor> urlInterceptorList = new ArrayList<UrlInterceptor>();
		urlInterceptorList = CollectionUtils.arrayToList(m.values().toArray(new UrlInterceptor[0]));
		Collections.sort(urlInterceptorList, new UrlInterceptorSortModel());
		// 依次添加
		for (UrlInterceptor urlInterceptor : urlInterceptorList) {
			String[] addPathPatterns = urlInterceptor.addPathPatterns();
			String[] excludePathPatterns = urlInterceptor.excludePathPatterns();
			InterceptorRegistration interceptorRegistration = registry.addInterceptor(urlInterceptor);
			// 添加拦截列表
			if (addPathPatterns != null) {
				interceptorRegistration.addPathPatterns(addPathPatterns);
			}

			// 添加过滤列表
			if (excludePathPatterns != null) {
				interceptorRegistration.excludePathPatterns(excludePathPatterns);
			}
		}
	}

	/**
	 * 添加异常解析器
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		Map<String, HandlerExceptionResolver> eMap = this.applicationContext
				.getBeansOfType(HandlerExceptionResolver.class);
		if (eMap != null && eMap.size() > 0) {
			exceptionResolvers.addAll(eMap.values());
		}
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// TODO Auto-generated method stub
		super.configureMessageConverters(converters);

		// http 处理字符串
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setDefaultCharset(DefaultCharSet.DefaultCharset);
		converters.add(stringConverter);

	}

}
