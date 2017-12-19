package com.fast.dev.component.data.sign.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.data.sign.interceptors.DataSignInterceptor;
import com.fast.dev.component.data.sign.model.DataSignConfig;

@Configuration
public class DataSignValidateConfiguration {

	/**
	 * 配置功能拦截器
	 * 
	 * @param dataSignConfig
	 * @return
	 */
	@Bean
	public DataSignInterceptor dataSignInterceptor(DataSignConfig dataSignConfig) {
		return new DataSignInterceptor(dataSignConfig);
	}

}
