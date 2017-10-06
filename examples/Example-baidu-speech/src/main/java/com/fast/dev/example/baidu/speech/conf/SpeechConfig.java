package com.fast.dev.example.baidu.speech.conf;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.baidu.speech.bean.BaiduSpeechKey;
import com.fast.dev.component.baidu.speech.service.SpeechService;
import com.fast.dev.component.baidu.speech.service.impl.SpeechServiceImpl;
import com.fast.dev.core.mark.TemplateMarkEngine;
import com.fast.dev.core.util.code.JsonUtil;

@Configuration
public class SpeechConfig {

	@Resource(name = "freeMarkEngineImpl")
	private TemplateMarkEngine templateMarkEngine;

	@Bean
	public SpeechService speechService() throws Exception {
		SpeechServiceImpl speechService = new SpeechServiceImpl();
		// 配置key
		BaiduSpeechKey baiduSpeechKey = JsonUtil.loadToObject("BaiduSpeechKey.json", BaiduSpeechKey.class);
		speechService.setBaiduSpeechKey(baiduSpeechKey);

		// 配置模版引擎
		speechService.setTemplateMarkEngine(this.templateMarkEngine);
		return speechService;
	}

}
