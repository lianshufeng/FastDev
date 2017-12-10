package com.fast.dev.example.ucloud.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.ucloud.file.model.UcloudFileConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.fast.dev", "com.demo.example" })
public class UcloudFileSrouceConfig{

	@Bean
	public UcloudFileConfig ucloudFileConfig() throws Exception {
		UcloudFileConfig uCloudFileConfig = JsonUtil.loadToObject("UcloudFileConfig.json", UcloudFileConfig.class);
		return uCloudFileConfig;
	}

}
