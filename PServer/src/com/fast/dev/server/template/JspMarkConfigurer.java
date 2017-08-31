package com.fast.dev.server.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class JspMarkConfigurer {

	/**
	 * JSP 视图解析器
	 * 
	 * @return
	 */
	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(JstlView.class);
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setOrder(5);
		return internalResourceViewResolver;
	}

}
