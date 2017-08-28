package com.bajie.project.server.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.bajie.project.core.util.file.PropertiesUtil;

/**
 * 上传文件的配置
 * 
 * @作者: 练书锋
 * @联系: 251708339@qq.com
 */
@Configuration
public class FileUploadConfig {
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		Properties properties = PropertiesUtil.loadProperties("fileupload.properties");
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(Long.parseLong(String.valueOf(properties.get("maxUploadSize"))));
		commonsMultipartResolver.setDefaultEncoding(String.valueOf(properties.get("defaultEncoding")));
		return commonsMultipartResolver;
	}
}