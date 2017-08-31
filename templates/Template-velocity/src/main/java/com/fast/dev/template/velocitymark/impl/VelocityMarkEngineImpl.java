package com.fast.dev.template.velocitymark.impl;

import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.fast.dev.core.mark.TemplateMarkEngine;

/**
 * Velocity 的实现类
 * 
 * @author 练书锋
 *
 */
@SuppressWarnings("deprecation")
@Component
public class VelocityMarkEngineImpl implements TemplateMarkEngine {

	private VelocityEngine velocityEngine;

	@Autowired
	private VelocityConfigurer velocityConfigurer;

	@PostConstruct
	private void init() {
		this.velocityEngine = velocityConfigurer.getVelocityEngine();
	}

	@Override
	public void writeStream(Writer writer, String templateName, Map<String, Object> o) {
		VelocityContext context = new VelocityContext();
		for (Entry<String, Object> entry : o.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		Template template = this.velocityEngine.getTemplate(templateName);
		template.merge(context, writer);
	}

}
