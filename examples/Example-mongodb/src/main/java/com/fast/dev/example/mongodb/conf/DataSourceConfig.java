package com.fast.dev.example.mongodb.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.hibernate.model.MongodbConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.demo.db.base", "com.demo.mongodb" })
public class DataSourceConfig {

	@Bean
	public MongodbConfig mongodbConfig() throws Exception {
		MongodbConfig mongodbConfig = JsonUtil.loadToObject("MongodbConfig.json", MongodbConfig.class);
		return mongodbConfig;
	}

}
