package com.fast.dev.template.thymeleaf.impl;

import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import com.fast.dev.core.mark.TemplateMarkEngine;

/**
 * Thymeleaf 引擎
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年6月13日
 *
 */
@Component
public class ThymeleafMarkEngineImpl implements TemplateMarkEngine {

	@Autowired
	private ITemplateEngine iTemplateEngine;
	
	
	@Override
	public void writeStream(Writer writer, String templateName, Map<String, Object> o) {
		Context context = new Context();
		context.setVariables(o);
		iTemplateEngine.process(templateName, context, writer);
	}

}
