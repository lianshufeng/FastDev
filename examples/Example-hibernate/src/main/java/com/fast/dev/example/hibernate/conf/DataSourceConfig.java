package com.fast.dev.example.hibernate.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.hibernate.bean.HibernateConfig;

@Configuration
@ComponentScan(basePackages = { "com.demo.hibernate" })
public class DataSourceConfig {

	@Bean
	public HibernateConfig hibernateConfig() throws Exception {
		HibernateConfig hibernateConfig = JsonUtil.loadToObject("HibernateConfig.json", HibernateConfig.class);
		return hibernateConfig;
	}

}
