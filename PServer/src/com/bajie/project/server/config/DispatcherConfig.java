package com.bajie.project.server.config;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.groovy.control.CompilationFailedException;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupView;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.bajie.project.core.ParentPlugin;
import com.bajie.project.core.interceptor.UrlInterceptor;
import com.bajie.project.core.model.UrlInterceptorSortModel;
import com.bajie.project.core.util.file.FileUtil;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

@Configuration
@EnableWebMvc
@SuppressWarnings({ "unchecked", "deprecation" })
public class DispatcherConfig extends WebMvcConfigurerAdapter {

	private static final String charset = "UTF-8";
	private static final Charset DefaultCharset = Charset.forName(charset);

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver contentView = new ContentNegotiatingViewResolver();
		// 添加视图解析器
		List<ViewResolver> viewResolverList = new ArrayList<ViewResolver>();
		viewResolverList.add(jspViewResolver());
		viewResolverList.add(freeMakerViewResolver());
		viewResolverList.add(groovyMarkupViewResolver());
		viewResolverList.add(velocityViewResolver());
		viewResolverList.add(thymeleafViewResolver());
		contentView.setViewResolvers(viewResolverList);
		// 设置默认视图
		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(jsonView());
		defaultViews.add(xmlView());
		contentView.setDefaultViews(defaultViews);
		return contentView;
	}

	// @Bean
	// public ReloadableResourceBundleMessageSource
	// reloadableResourceBundleMessageSource() {
	// ReloadableResourceBundleMessageSource
	// reloadableResourceBundleMessageSource = new
	// ReloadableResourceBundleMessageSource();
	// reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
	// reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(false);
	// return reloadableResourceBundleMessageSource;
	// }

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

	/**
	 * Thymeleaf 模版
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(thymeleafTemplateEngine());
		thymeleafViewResolver.setViewClass(ThymeleafView.class);
		thymeleafViewResolver.setViewNames(new String[] { "*.htl" });
		thymeleafViewResolver.setContentType("text/html; charset=" + charset);
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
		return springTemplateEngine;
	}

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
		groovyMarkupViewResolver.setContentType("text/html; charset=" + charset);
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
		configuration.setDeclarationEncoding(charset);
		return configuration;
	}

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
		freeMarkerViewResolver.setContentType("text/html; charset=" + charset);
		freeMarkerViewResolver.setExposeRequestAttributes(true);
		freeMarkerViewResolver.setExposeSessionAttributes(true);
		freeMarkerViewResolver.setExposeSpringMacroHelpers(true);
		freeMarkerViewResolver.setRequestContextAttribute("request");
		freeMarkerViewResolver.setOrder(1);
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
		freeMarkerConfigurer.setDefaultEncoding(charset);
		freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/ftl/");
		Binding binding = new Binding();
		GroovyShell shell = new GroovyShell(binding);
		binding.setVariable("applicationContext", applicationContext);
		URL url = FileUtil.readFileUrl("freemarkEV.grovvy");
		Object object = shell.evaluate(new File(url.toURI()));
		if (object instanceof Map) {
			freeMarkerConfigurer.setFreemarkerVariables((Map<String, Object>) object);
		}
		return freeMarkerConfigurer;
	}

	/**
	 * velocity视图解析器
	 * 
	 * @return
	 */
	@Bean
	public VelocityViewResolver velocityViewResolver() {
		VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
		velocityViewResolver.setViewClass(VelocityView.class);
		velocityViewResolver.setViewNames("*.vtl");
		velocityViewResolver.setContentType("text/html; charset=" + charset);
		velocityViewResolver.setExposeRequestAttributes(true);
		velocityViewResolver.setExposeSessionAttributes(true);
		velocityViewResolver.setExposeSpringMacroHelpers(true);
		velocityViewResolver.setRequestContextAttribute("request");
		velocityViewResolver.setOrder(2);
		return velocityViewResolver;
	}

	/**
	 * velocity 配置
	 * 
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws CompilationFailedException
	 */
	@Bean
	public VelocityConfigurer velocityConfigurer() throws Exception {
		VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
		velocityConfigurer.setVelocityPropertiesMap(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("input.encoding", charset);
				put("output.encoding", charset);
			}
		});
		velocityConfigurer.setResourceLoaderPath("/WEB-INF/vtl/");
		return velocityConfigurer;
	}

	/**
	 * JSP 视图解析器
	 * 
	 * @return
	 */
	@Bean(name = "jspViewResolver")
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setViewClass(JstlView.class);
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setOrder(5);
		return internalResourceViewResolver;
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
		// 插件排除对URL的拦截
		final Collection<ParentPlugin> parentPlugin = this.applicationContext.getBeansOfType(ParentPlugin.class)
				.values();
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
			String[] excludeUrls = mergeExcludeUrl(parentPlugin, interceptorCache.get(urlInterceptor),
					excludePathPatterns);
			interceptorRegistration.excludePathPatterns(excludeUrls);
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
		stringConverter.setDefaultCharset(DefaultCharset);
		converters.add(stringConverter);

	}

	/**
	 * 合并排除拦截器
	 * 
	 * @param buildPlugins
	 *            所有的插件
	 * @param springBeanName
	 *            当前的bean对象
	 * @param defaultExclude
	 *            默认的拦截器
	 * @return
	 */
	private String[] mergeExcludeUrl(Collection<ParentPlugin> buildPlugins, String springBeanName,
			String[] defaultExclude) {
		List<String> list = new ArrayList<String>();
		for (ParentPlugin buildPlugin : buildPlugins) {
			Map<String, String[]> pluginExcludeIntercepts = buildPlugin.excludeIntercepts();
			if (pluginExcludeIntercepts != null) {
				String[] urls = pluginExcludeIntercepts.get(springBeanName);
				if (urls != null) {
					for (String url : urls) {
						String excludeUrl = url.replaceAll("//", "/");
						list.add(excludeUrl);
					}
				}
			}
		}

		if (defaultExclude != null) {
			for (String url : defaultExclude) {
				list.add(url);
			}
		}

		return list.toArray(new String[list.size()]);
	}

}
