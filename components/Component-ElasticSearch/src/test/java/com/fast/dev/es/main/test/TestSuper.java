package com.fast.dev.es.main.test;

import org.springframework.context.ApplicationContext;

import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.helper.ESHelper;
import com.fast.dev.es.main.boot.BootUtil;

public abstract class TestSuper {

	static ApplicationContext applicationContext = BootUtil.applicationContext();

	static ESHelper esHelper = applicationContext.getBean(ESHelper.class);
	
	static ESDao esDao = esHelper.dao("test", "test" );

}
