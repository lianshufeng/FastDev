package com.fast.dev.template.velocitymark.config;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.fast.dev.core.charset.DefaultCharSet;

@Configuration

@SuppressWarnings({ "deprecation" })
public class VelocityMarkConfigurer {

	@Bean
	public VelocityViewResolver velocityViewResolver() {
		VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
		velocityViewResolver.setViewClass(VelocityView.class);
		velocityViewResolver.setViewNames("*.vtl");
		velocityViewResolver.setContentType("text/html; charset=" + DefaultCharSet.charset);
		velocityViewResolver.setExposeRequestAttributes(true);
		velocityViewResolver.setExposeSessionAttributes(true);
		velocityViewResolver.setExposeSpringMacroHelpers(true);
		velocityViewResolver.setRequestContextAttribute("request");
		velocityViewResolver.setOrder(2);
		return velocityViewResolver;
	}

	@Bean
	public VelocityConfigurer velocityConfigurer() throws Exception {
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setVelocityPropertiesMap(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("input.encoding", DefaultCharSet.charset);
				put("output.encoding", DefaultCharSet.charset);
			}
		});
		velocityConfigurer.setResourceLoaderPath("/WEB-INF/vtl/");
		return velocityConfigurer;
	}
}
