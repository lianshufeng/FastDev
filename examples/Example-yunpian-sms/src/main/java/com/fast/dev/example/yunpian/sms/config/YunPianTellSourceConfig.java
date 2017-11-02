package com.fast.dev.example.yunpian.sms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.yunpian.sms.model.YunPianConfig;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
@ComponentScan(basePackages = { "com.fast.dev", "com.fast.dev.example" })
public class YunPianTellSourceConfig {

	@Bean
	public YunPianConfig yunPianTellConfig() throws Exception {
		YunPianConfig yunPianConfig = JsonUtil.loadToObject("YunPianSmsConfig.json", YunPianConfig.class);
		return yunPianConfig;
	}

}
