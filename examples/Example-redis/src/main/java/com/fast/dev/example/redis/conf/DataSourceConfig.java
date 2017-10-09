package com.fast.dev.example.redis.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.redis.model.RedisConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.demo.db.base", "com.demo.redis" })
public class DataSourceConfig {

	@Bean
	public RedisConfig hibernateConfig() throws Exception {
		RedisConfig hibernateConfig = JsonUtil.loadToObject("RedisConfig.json", RedisConfig.class);
		return hibernateConfig;
	}

}
