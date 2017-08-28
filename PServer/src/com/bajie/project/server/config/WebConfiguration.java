package com.bajie.project.server.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * web配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年5月10日
 *
 */
//@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

	/**
	 * 防止xss ，转义html
	 */
	// @Bean
	// public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
	// RequestMappingHandlerAdapter adapter = new
	// RequestMappingHandlerAdapter();
	// adapter.setWebBindingInitializer(xssInitializer);
	// return adapter;
	// }
}