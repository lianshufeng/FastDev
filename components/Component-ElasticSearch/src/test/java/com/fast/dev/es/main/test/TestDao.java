package com.fast.dev.es.main.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.fast.dev.es.helper.ESHelper;
import com.fast.dev.es.main.boot.BootUtil;

public class TestDao {

	static ApplicationContext applicationContext = BootUtil.applicationContext();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ESHelper esHelper = applicationContext.getBean(ESHelper.class);
		for (int i = 0; i < 10; i++) {
			System.out.println(esHelper.dao("test", "test" + i));
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(esHelper.dao("test" + i, "test" + i));
		}
	}

}
