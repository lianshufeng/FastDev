package com.fast.dev.template.thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.fast.dev.core.charset.DefaultCharSet;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class ThymeleafMarkConfigurer {
	/**
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(thymeleafTemplateEngine());
		thymeleafViewResolver.setViewClass(ThymeleafView.class);
		thymeleafViewResolver.setViewNames(new String[] { "*.htl" });
		thymeleafViewResolver.setContentType("text/html; charset=" + DefaultCharSet.charset);
		thymeleafViewResolver.setOrder(4);
		thymeleafViewResolver.setTemplateEngine(thymeleafTemplateEngine());
		return thymeleafViewResolver;
	}

	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/htl/");
		templateResolver.setCharacterEncoding("UTF-8");
		// templateResolver.setSuffix(".htl");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * 模版引擎解析器
	 * 
	 * @return
	 */
	@Bean
	public ITemplateEngine thymeleafTemplateEngine() {
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		springTemplateEngine.setTemplateResolver(thymeleafTemplateResolver());
		// 布局方言
		springTemplateEngine.addDialect(layoutDialect());
		return springTemplateEngine;
	}

	/**
	 * 布局方言
	 * 
	 * @return
	 */
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}

}
