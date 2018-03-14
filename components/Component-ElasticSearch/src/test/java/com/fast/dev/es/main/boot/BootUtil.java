package com.fast.dev.es.main.boot;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fast.dev.es.conf.ESConfig;

public class BootUtil {

	/**
	 * 获取启动后的application
	 * 
	 * @return
	 */
	public static ApplicationContext applicationContext() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		ConfigurableListableBeanFactory configurableListableBeanFactory = applicationContext.getBeanFactory();
		ESConfig esConfig = new ESConfig();
		esConfig.setHosts(new String[] { "127.0.0.1:9300" });
		configurableListableBeanFactory.registerSingleton("esConfig", esConfig);
		applicationContext.register(ESConfiguration.class);
		applicationContext.refresh();
		return applicationContext;
	}

}
