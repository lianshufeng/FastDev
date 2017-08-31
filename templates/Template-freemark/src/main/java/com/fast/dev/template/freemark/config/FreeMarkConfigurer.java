package com.fast.dev.template.freemark.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.fast.dev.core.charset.DefaultCharSet;

@Configuration
public class FreeMarkConfigurer {

	/**
	 * freemark视图解析器
	 * 
	 * @return
	 */
	@Bean
	public FreeMarkerViewResolver freeMakerViewResolver() {
		FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
		freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
		freeMarkerViewResolver.setViewNames("*.ftl");
		freeMarkerViewResolver.setContentType("text/html; charset=" + DefaultCharSet.charset);
		freeMarkerViewResolver.setExposeRequestAttributes(true);
		freeMarkerViewResolver.setExposeSessionAttributes(true);
		freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
		freeMarkerViewResolver.setRequestContextAttribute("request");
		freeMarkerViewResolver.setOrder(2);
		return freeMarkerViewResolver;
	}

	/**
	 * FreeMarker配置
	 * 
	 * @return
	 */
	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() throws Exception {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setDefaultEncoding(DefaultCharSet.charset);
		freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/ftl/");
		// binding.setVariable("applicationContext", applicationContext);
		// Binding binding = new Binding();
		// GroovyShell shell = new GroovyShell(binding);
		// URL url = FileUtil.readFileUrl("freemarkEV.grovvy");
		// Object object = shell.evaluate(new File(url.toURI()));
		// if (object instanceof Map) {
		// freeMarkerConfigurer.setFreemarkerVariables((Map<String, Object>) object);
		// }
		return freeMarkerConfigurer;
	}

}
