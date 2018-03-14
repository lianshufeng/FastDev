package com.fast.dev.es.main.test;

import java.util.Set;

import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.main.test.model.UserModel;

public class TestGet extends TestSuper {

	@Test
	public void testGet() throws Exception {
		Object[] userModels = new UserModel[10];
		for (int i = 0; i < userModels.length; i++) {
			userModels[i] = new UserModel("id_get", "test_get", i);
		}
		Set<String> ids = esDao.save(userModels).keySet();
		System.out.println("get : " + JsonUtil.toJson(esDao.get(ids.toArray(new String[ids.size()]))));
	}

}
