package com.fast.dev.es.main.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.main.test.model.UserModel;

public class TestSave extends TestSuper {

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
	public void saveMap() throws Exception {
		HashMap<String, Object> m = new HashMap<>();
		m.put("info", "demo");
		m.put("time", System.currentTimeMillis());
		System.out.println(JsonUtil.toJson(esDao.save(m)));
	}

	@Test
	public void saveMaps() throws Exception {
		HashMap<String, Object> m1 = new HashMap<>();
		m1.put("info", UUID.randomUUID().toString());
		m1.put("time", System.currentTimeMillis());

		HashMap<String, Object> m2 = new HashMap<>();
		m2.put("info", UUID.randomUUID().toString());
		m2.put("time", System.currentTimeMillis());

		UserModel userModel = new UserModel("uId", "xiaofeng", 1);
		System.out.println(JsonUtil.toJson(esDao.save(m1, m2, userModel)));
	}

	@Test
	public void update() throws Exception {
		// 插入10条记录
		for (int i = 0; i < 10; i++) {
			esDao.save("test" + i, new UserModel("uId" + i, "xiaofeng", i));
		}
		// 故意修改错误
		System.out.println("update:" + esDao.update("testerror", new HashMap<>()));
		// 单挑修改
		System.out.println("update :  " + esDao.update("test0", new UserModel("000", "00", 0)));
		// 批量修改
		System.out.println("update :  " + esDao.update(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				for (int i = 0; i < 10; i++) {
					put("test" + i, new HashMap<String, Serializable>() {
						private static final long serialVersionUID = 1L;

						{
							put("name", "xiaoxue");
						}
					});
				}
			}
		}));

	}

}
