package com.fast.dev.example.hibernate.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.hibernate.model.HibernateConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.demo.db.base", "com.demo.hibernate" })
public class DataSourceConfig {

	@Bean
	public HibernateConfig hibernateConfig() throws Exception {
		HibernateConfig hibernateConfig = JsonUtil.loadToObject("HibernateConfig.json", HibernateConfig.class);
		return hibernateConfig;
	}

}
