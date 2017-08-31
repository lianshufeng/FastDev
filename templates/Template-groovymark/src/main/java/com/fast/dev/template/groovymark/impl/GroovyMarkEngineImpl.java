package com.fast.dev.template.groovymark.impl;

import java.io.Writer;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;

import com.fast.dev.core.mark.TemplateMarkEngine;

import groovy.lang.Writable;
import groovy.text.Template;
import groovy.text.markup.MarkupTemplateEngine;

/**
 * groovy引擎的实现代码
 * @author 练书锋
 *
 */
@Component
public class GroovyMarkEngineImpl implements TemplateMarkEngine {

	private MarkupTemplateEngine markupTemplateEngine;

	@Autowired
	private GroovyMarkupConfigurer groovyMarkupConfigurer;

	@PostConstruct
	private void init() {
		markupTemplateEngine = this.groovyMarkupConfigurer.getTemplateEngine();
	}

	@Override
	public void writeStream(Writer writer, String templateName, Map<String, Object> o) {
		try {
			Template template = markupTemplateEngine.createTemplateByPath(templateName);
			Writable writable = template.make(o);
			writable.writeTo(writer);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
