package com.fast.dev;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.catalina.ssi.SSIServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.fast.dev.core.init.ResourcesInit;

/**
 * Application Lifecycle Listener implementation class Bootstrap
 *
 */
public class Bootstrap implements WebApplicationInitializer {

	/**
	 * Default constructor.
	 */
	public Bootstrap() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStartup(ServletContext appContext) throws ServletException {
		// 初始化资源
		ResourcesInit.init(appContext.getRealPath("/"));
		// 配置spring
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.scan(this.getClass().getPackage().getName());
		// 添加上下文监听
		appContext.addListener(new ContextLoaderListener(applicationContext));
		// 初始化默认的 Dispatcher
		addServlet("dispatcher", "/", 1, appContext, applicationContext, true);

		// 增加shtml的支持
		addSSIServlet(appContext, applicationContext);

	}

	/**
	 * 添加 Servlet
	 * 
	 * @param name
	 * @param url
	 * @param order
	 * @param appContext
	 * @param applicationContext
	 * @param isAsyncSupported
	 */
	private void addServlet(String name, String url, int order, ServletContext appContext,
			AnnotationConfigWebApplicationContext applicationContext, boolean isAsyncSupported) {
		ServletRegistration.Dynamic dispatcher = appContext.addServlet(name, new DispatcherServlet(applicationContext));
		dispatcher.addMapping(url);
		dispatcher.setLoadOnStartup(order);
		dispatcher.setAsyncSupported(isAsyncSupported);
	}

	/**
	 * 增加shtml的支持
	 * 
	 * @param appContext
	 * @param applicationContext
	 */
	private void addSSIServlet(ServletContext appContext, AnnotationConfigWebApplicationContext applicationContext) {
		ServletRegistration.Dynamic dynamic = appContext.addServlet("SSIServlet", new SSIServlet());
		dynamic.addMapping("*.shtml");
		dynamic.setInitParameter("inputEncoding", "utf-8");
		dynamic.setInitParameter("outputEncoding", "utf-8");
	}

}
