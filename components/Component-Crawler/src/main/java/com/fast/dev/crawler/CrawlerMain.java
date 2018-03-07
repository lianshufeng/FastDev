package com.fast.dev.crawler;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StreamUtils;

import com.fast.dev.component.mongodb.model.MongodbConfig;
import com.fast.dev.core.util.code.JsonUtil;

public class CrawlerMain {
	private static final Logger Log = Logger.getLogger(CrawlerMain.class);
	// spring容器
	public static final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

	public static void main(String[] args) throws Exception {
		MongodbConfig mongodbConfig = loadDBConfig();
		ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
		beanFactory.registerSingleton("mongodbConfig", mongodbConfig);
		applicationContext.scan("com.fast.dev");
		applicationContext.refresh();
		Log.info("Start ...");
	}

	/**
	 * 载入mongodb配置
	 * 
	 * @return
	 * @throws Exception
	 */
	private static MongodbConfig loadDBConfig() throws Exception {
		InputStream inputStream = CrawlerMain.class.getResourceAsStream("/mongodb.json");
		byte[] bin = StreamUtils.copyToByteArray(inputStream);
		return JsonUtil.toObject(new String(bin), MongodbConfig.class);
	}
}
