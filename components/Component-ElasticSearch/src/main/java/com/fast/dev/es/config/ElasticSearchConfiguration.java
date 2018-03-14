package com.fast.dev.es.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.fast.dev.es.conf.ESConfig;
import com.fast.dev.es.factory.ESFactory;
import com.fast.dev.es.helper.ESHelper;

/**
 * 配置请集成该类并使用注解 @Configuration
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */
@ComponentScan(basePackages = "com.fast.dev.es")
public abstract class ElasticSearchConfiguration {

	static Log log = LogFactory.getLog(ElasticSearchConfiguration.class);

	/**
	 * 注册客户端
	 * 
	 * @param esConfig
	 * @return
	 */
	@Bean
	public Client esClient(ESConfig esConfig) {
		Client client = ESFactory.buildClient(esConfig.getHosts());
		log.info(String.format("Create ES Client : [ %s ]", client));
		return client;
	}

	/**
	 * 注册ES工具
	 * 
	 * @return
	 */
	@Bean
	public ESHelper esHelper() {
		return new ESHelper();
	}

}
