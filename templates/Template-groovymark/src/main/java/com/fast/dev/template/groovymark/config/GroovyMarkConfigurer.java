package com.fast.dev.template.groovymark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupView;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

import com.fast.dev.core.charset.DefaultCharSet;

@Configuration
public class GroovyMarkConfigurer {

	/**
	 * freemark视图解析器
	 * 
	 * @return
	 */

	/**
	 * Groovy 模版视图解析器
	 * 
	 * @return
	 */
	@Bean
	public GroovyMarkupViewResolver groovyMarkupViewResolver() {
		GroovyMarkupViewResolver groovyMarkupViewResolver = new GroovyMarkupViewResolver();
		groovyMarkupViewResolver.setViewClass(GroovyMarkupView.class);
		groovyMarkupViewResolver.setViewNames("*.gtl");
		groovyMarkupViewResolver.setContentType("text/html; charset=" + DefaultCharSet.charset);
		groovyMarkupViewResolver.setExposeRequestAttributes(true);
		groovyMarkupViewResolver.setExposeSessionAttributes(true);
		groovyMarkupViewResolver.setExposeSpringMacroHelpers(true);
		groovyMarkupViewResolver.setRequestContextAttribute("request");
		groovyMarkupViewResolver.setOrder(3);
		return groovyMarkupViewResolver;
	}

	/**
	 * Groovy 配置
	 * 
	 * @return
	 */
	@Bean
	public GroovyMarkupConfigurer groovyMarkupConfigurer() {
		GroovyMarkupConfigurer configuration = new GroovyMarkupConfigurer();
		configuration.setResourceLoaderPath("/WEB-INF/gtl/");
		configuration.setUseDoubleQuotes(true);
		configuration.setAutoNewLine(true);
		configuration.setAutoIndent(true);
		configuration.setDeclarationEncoding(DefaultCharSet.charset);
		return configuration;
	}

}
