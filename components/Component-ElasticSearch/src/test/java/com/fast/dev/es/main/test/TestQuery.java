package com.fast.dev.es.main.test;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.es.query.QueryHighlight;
import com.fast.dev.es.query.QueryLimit;
import com.fast.dev.es.query.QueryMatch;
import com.fast.dev.es.query.QueryPhrase;
import com.fast.dev.es.query.QueryResult;
import com.fast.dev.es.query.QuerySort;

@SuppressWarnings("serial")
public class TestQuery extends TestSuper {

	@Test
	public void testQuery() throws Exception {

		// 分词匹配

		QueryPhrase queryPhrase = new QueryPhrase(new ArrayList<QueryMatch>() {
			{
				add(new QueryMatch("info", "中国"));
				add(new QueryMatch("info", "努力"));
				add(new QueryMatch("bool", true));
			}
		}, false);

		// 分页
		QueryLimit queryLimit = new QueryLimit();
		queryLimit.setSize(30);
		queryLimit.setFrom(0);
		queryLimit.setTimeout(1l);

		// 高亮
		List<QueryHighlight> queryHighlights = new ArrayList<>();
		queryHighlights.add(new QueryHighlight("info", "<!-", "-!>"));

		List<QuerySort> querySorts = new ArrayList<>();
		querySorts.add(new QuerySort("time", SortOrder.ASC));
		QueryResult queryResult = esDao.list(queryPhrase, queryHighlights, querySorts, queryLimit);
		System.out.println("size : " + queryResult.getRecords().size());
		System.out.println(JsonUtil.toJson(queryResult));

	}

}
