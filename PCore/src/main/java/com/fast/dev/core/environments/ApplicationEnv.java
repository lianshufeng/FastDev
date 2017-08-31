package com.fast.dev.core.environments;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationEnv {

	// 应用的真实文件路径
	private static File applicationRealPath;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	private void init() {
		try {
			applicationRealPath = this.applicationContext.getResource(".").getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取项目路径
	 * 
	 * @return
	 */
	public static File getApplicationRealPath() {
		return applicationRealPath;
	}

}
