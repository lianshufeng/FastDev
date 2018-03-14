package com.fast.dev.es.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.helper.ESHelper;

@Component
public class TestDao {

	private ESDao esDao = null;

	@Autowired
	private ESHelper eSHelper;

	@Autowired
	private void init() {
		this.esDao = eSHelper.dao("test", "test");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// testAdd();
					testUpdate();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void testAdd() throws Exception {
		HashMap<String, Object> m1 = new HashMap<String, Object>();
		m1.put("info", "haihao");
		m1.put("time", System.currentTimeMillis());

		HashMap<String, Object> m2 = new HashMap<>();
		m2.put("info", "为了啥");
		m2.put("time", System.currentTimeMillis());

		String[] ids = this.esDao.save(m1, m2, new TestUserDemo(UUID.randomUUID().toString(), "xiaofeng", 1));

		System.out.println("批量插入数据：" + JsonUtil.toJson(ids));
	}

	private void testUpdate() throws Exception {
		HashMap<String, Object> m1 = new HashMap<String, Object>();
		m1.put("info", "haihao");
		m1.put("info1", "haihao");
		m1.put("time", System.currentTimeMillis());

		HashMap<String, Object> m2 = new HashMap<>();
		m2.put("info", "为了啥");
		m2.put("info2", "为了啥，多余的信息");

		System.out.println("修改一条数据" + this.esDao.save("test1", m1));
		System.out.println("修改一条数据" + this.esDao.update("test1", m2));

		// 测试批量修改,现插入数据，然后在批量修改
		Map<String, Serializable> updateMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			final int index = i;
			String id = "testID_" + i;
			updateMap.put(id, new HashMap<String, Serializable>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("hash", "hash..完全相同则表示修改成功");
				}
			});
			this.esDao.save(id, new HashMap<String, Serializable>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					put("i", index);
					put("hash", UUID.randomUUID().toString());
				}
			});
			System.out.println();
		}
		updateMap.put("testError", "故意写错");
		System.out.println("批量修改：" + this.esDao.update(updateMap));
	}

}
