package com.fast.dev.es.main.test;

import java.util.Set;

import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.main.test.model.UserModel;

public class TestRemove extends TestSuper {

	@Test
	public void remove() throws Exception {
		// 保存数据
		Object[] userModels = new UserModel[10];
		for (int i = 0; i < userModels.length; i++) {
			userModels[i] = new UserModel("id_get", "test_get", i);
		}
		Set<String> idSet = esDao.save(userModels).keySet();
		String[] ids = idSet.toArray(new String[idSet.size()]);
		// 获取数据
		System.out.println("get : " + JsonUtil.toJson(esDao.get(ids)));
		// 删除数据
		System.out.println("remove:" + esDao.remove(ids));
		// 再获取数据
		System.out.println("get : " + JsonUtil.toJson(esDao.get(ids)));
	}

}
