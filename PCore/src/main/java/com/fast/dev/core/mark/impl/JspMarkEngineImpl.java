package com.fast.dev.core.mark.impl;

import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fast.dev.core.mark.TemplateMarkEngine;

/**
 * JSP 实现 ,暂未实现，因为必须通过 request 与 respose 才能转换...
 * 
 * @author 练书锋
 *
 */
//@Component
@SuppressWarnings("unused")
public class JspMarkEngineImpl implements TemplateMarkEngine {

	@Autowired
	private AnnotationConfigWebApplicationContext applicationContext;

	@Resource(name = "jspViewResolver")
	private InternalResourceViewResolver jspViewResolver;

	private ServletContext servletContext;

	@Autowired
	private void init() {
		servletContext = applicationContext.getServletContext();
	}

	@Override
	public void writeStream(Writer writer, String templateName, Map<String, Object> o) {
		try {
			JstlView view = (JstlView) jspViewResolver.resolveViewName(templateName, null);
//			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(view.getUrl());

			
			// 创建临时的请求与响应对象
//			 view.render(o, request, response);

			
			 
			 
			System.out.println(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// servletContext.getRequestDispatcher(requestPageUrl)
	}

	
}
