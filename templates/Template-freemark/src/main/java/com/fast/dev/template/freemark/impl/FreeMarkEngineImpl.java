package com.fast.dev.template.freemark.impl;

import java.io.Writer;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fast.dev.core.mark.TemplateMarkEngine;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * freeMark 实现
 * @author 练书锋
 *
 */
@Component
public class FreeMarkEngineImpl implements TemplateMarkEngine {

	private Configuration freeMarkconfiguration;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@PostConstruct
	private void init() {
		this.freeMarkconfiguration = freeMarkerConfigurer.getConfiguration();
	}

	@Override
	public void writeStream(Writer writer, String templateName, Map<String, Object> o) {
		try {
			Template template = freeMarkconfiguration.getTemplate(templateName);
			writer.write(FreeMarkerTemplateUtils.processTemplateIntoString(template, o));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
