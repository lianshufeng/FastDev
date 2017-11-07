package com.fast.dev.example.data.sign.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.data.sign.model.DataSignConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
public class DataSignConfiguration {

	@Bean
	public DataSignConfig dataSignConfig() throws Exception {
		DataSignConfig dataSignConfig = 	JsonUtil.loadToObject("DataSignConfig.json", DataSignConfig.class);
		return dataSignConfig;
	}

}
