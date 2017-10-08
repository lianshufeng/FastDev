package com.fast.dev.example.mybatis.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.mybatis.bean.MyBatisConfig;

@Configuration
@ComponentScan(basePackages = { "com.demo.mybatis" })
public class DataSourceConfig {

	@Bean
	public MyBatisConfig myBatisConfig() throws Exception {
		MyBatisConfig config = JsonUtil.loadToObject("MybatisConfig.json", MyBatisConfig.class);
		return config;
	}

}
