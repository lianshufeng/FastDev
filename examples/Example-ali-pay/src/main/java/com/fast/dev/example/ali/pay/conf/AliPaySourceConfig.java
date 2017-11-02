package com.fast.dev.example.ali.pay.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.ali.pay.model.AliPayConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.fast.dev", "com.demo.example" })
public class AliPaySourceConfig {

	@Bean
	public AliPayConfig aliPayConfig() throws Exception {
		AliPayConfig aliPayConfig = JsonUtil.loadToObject("AliPayConfig.json", AliPayConfig.class);
		return aliPayConfig;
	}

}
