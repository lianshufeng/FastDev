package com.fast.dev.es.main.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.query.QueryLimit;
import com.fast.dev.es.query.QueryPhrase;
import com.fast.dev.es.query.QueryResult;
import com.fast.dev.es.query.QuerySort;

public class TestQuery extends TestSuper {

	@Test
	public void testQuery() throws Exception {

		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> m = new HashMap<>();
			m.put("test" + i, 1);
			m.put("name", i + "中文可以随便分词_"  + System.currentTimeMillis());
			m.put("time", System.currentTimeMillis());
			list.add(m);
		}

		System.out.println(esDao.save(list.toArray()));

		QueryPhrase[] query = new QueryPhrase[1];
		QueryPhrase queryPhrase = new QueryPhrase();
		query[0] = queryPhrase;

		queryPhrase.setName("name");
		queryPhrase.setValue("随便");

		//分页
		QueryLimit queryLimit = new QueryLimit();
		queryLimit.setSize(30);
		queryLimit.setFrom(10);
		queryLimit.setTimeout(1000l);
		
		
		
		
		QuerySort[] querySort = new QuerySort[1];
		querySort[0] = new QuerySort();
		querySort[0].setField("time");
		querySort[0].setOrder(SortOrder.DESC);
		QueryResult queryResult = esDao.list(query, null, querySort, queryLimit);
		System.out.println("size : " + queryResult.getRecords().length);
		System.out.println(JsonUtil.toJson(queryResult));
		
	}

}
