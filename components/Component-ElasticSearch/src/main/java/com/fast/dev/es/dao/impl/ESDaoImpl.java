package com.fast.dev.es.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.query.QueryHighlight;
import com.fast.dev.es.query.QueryLimit;
import com.fast.dev.es.query.QueryMatch;
import com.fast.dev.es.query.QueryPhrase;
import com.fast.dev.es.query.QueryRecord;
import com.fast.dev.es.query.QueryResult;
import com.fast.dev.es.query.QuerySort;
import com.fast.dev.es.util.ObjectUtil;

/**
 * ES的数据操作持久层
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */
@Component
@Scope("prototype")
public class ESDaoImpl implements ESDao {

	// es客户端
	@Autowired
	private Client client;

	@PreDestroy
	private void shutdown() {
		if (client != null) {
			client.close();
		}
	}

	// 索引(库)
	private String index;
	// 类型(表)
	private String type;

	/**
	 * 配置
	 * 
	 * @param indexNAme
	 * @param typeName
	 */
	public void config(String indexName, String typeName) {
		this.index = indexName;
		this.type = typeName;
	}

	@Override
	public boolean save(String id, Object source) {
		if (StringUtils.isEmpty(id) || source == null) {
			return false;
		}
		IndexResponse indexResponse = this.client.prepareIndex(index, type, id).setSource(toMap(source)).get();
		return id.equals(indexResponse.getId());
	}

	@Override
	public LinkedHashMap<String, String> save(Object... sources) {
		if (sources == null) {
			return null;
		}
		if (sources.length == 0) {
			return new LinkedHashMap<>();
		}

		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (Object source : sources) {
			if (source != null) {
				IndexRequestBuilder request = this.client.prepareIndex(index, type).setSource(toMap(source));
				bulkRequest.add(request);
			}
		}
		return executeBulkRequestBuilder(bulkRequest);
	}

	@Override
	public Map<String, Object> get(String... ids) {
		if (ids == null) {
			return null;
		}
		if (ids.length == 0) {
			return new HashMap<>();
		}
		Map<String, Object> result = new HashMap<>();
		MultiGetRequestBuilder multiGetRequestBuilder = this.client.prepareMultiGet().add(index, type, ids);
		MultiGetResponse multiGetResponse = multiGetRequestBuilder.get();
		for (MultiGetItemResponse response : multiGetResponse.getResponses()) {
			result.put(response.getId(), response.getResponse().getSource());
		}
		return result;
	}

	@Override
	public Map<String, String> remove(String... ids) {
		if (ids == null) {
			return null;
		}
		if (ids.length == 0) {
			return new HashMap<>();
		}
		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (String id : ids) {
			bulkRequest.add(this.client.prepareDelete(index, type, id));
		}
		return executeBulkRequestBuilder(bulkRequest);
	}

	@Override
	public Boolean update(final String id, final Object source) {
		if (StringUtils.isEmpty(id) || source == null) {
			return false;
		}
		Map<String, String> result = this.update(new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put(id, source);
			}
		});
		return result.containsKey(id) && result.get(id) == null;
	}

	@Override
	public Map<String, String> update(Map<String, Object> sources) {
		if (sources == null || sources.size() == 0) {
			return null;
		}
		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (Entry<String, Object> entry : sources.entrySet()) {
			String id = entry.getKey();
			Object obj = entry.getValue();
			// 创建批量请求对象
			UpdateRequestBuilder requestBuilder = this.client.prepareUpdate(index, type, id);
			requestBuilder.setDoc(toMap(obj));
			bulkRequest.add(requestBuilder);
		}
		return executeBulkRequestBuilder(bulkRequest);
	}

	/**
	 * 自定义条件查询
	 * 
	 * @param queryBuilder
	 * @param queryHighlights
	 * @param sorts
	 * @param queryLimit
	 */
	@Override
	public QueryResult list(QueryBuilder queryBuilder, Collection<QueryHighlight> queryHighlights,
			Collection<QuerySort> sorts, QueryLimit queryLimit) {
		return list(queryBuilder, 40, queryHighlights, sorts, queryLimit);
	}

	/**
	 * 查询
	 * 
	 * @param queryPhrases
	 *            短语匹配查询，多条用or连接
	 * @param queryHighlights
	 *            高亮规则
	 * @param sorts
	 *            排序
	 * @param queryLimit
	 *            限制结果
	 * @return
	 */
	@Override
	public QueryResult list(QueryPhrase queryPhrase, Collection<QueryHighlight> queryHighlights,
			Collection<QuerySort> sorts, QueryLimit queryLimit) {
		return this.list(getQueryPhrase(queryPhrase), queryHighlights, sorts, queryLimit);
	}

	@Override
	public QueryResult list(QueryBuilder queryBuilder, int fragmentSize, Collection<QueryHighlight> queryHighlights,
			Collection<QuerySort> sorts, QueryLimit queryLimit) {
		// 创建查询请求对象
		SearchRequestBuilder requestBuilder = this.client.prepareSearch(index);
		requestBuilder.setTypes(type);

		// 检索方式
		requestBuilder.setSearchType(SearchType.DEFAULT);

		// 设置查询条件
		setQueryLimit(requestBuilder, queryLimit);

		// 设置排序规则
		setQuerySort(requestBuilder, sorts);

		// 设置高亮
		setQueryHighlight(requestBuilder, fragmentSize, queryHighlights);

		// 设置查询
		requestBuilder.setQuery(queryBuilder);

		// 执行查询
		return executeQuery(requestBuilder);
	}

	/**
	 * 执行查询
	 * 
	 * @param requestBuilder
	 * @return
	 */
	private QueryResult executeQuery(final SearchRequestBuilder requestBuilder) {
		SearchResponse responsebuilder = requestBuilder.get();
		SearchHits searchHits = responsebuilder.getHits();
		QueryResult queryResult = new QueryResult();
		// 总量
		queryResult.setTotal(searchHits.getTotalHits());
		List<QueryRecord> queryRecords = new ArrayList<>();
		// 循环所有记录
		for (SearchHit searchHit : searchHits.getHits()) {
			QueryRecord queryRecord = new QueryRecord();
			// 源数据
			queryRecord.setSource(searchHit.getSourceAsMap());
			// 高亮规则
			Map<String, Collection<String>> highLightMap = new HashMap<>();
			for (Entry<String, HighlightField> entry : searchHit.getHighlightFields().entrySet()) {
				List<String> fragments = new ArrayList<>();
				for (Text fragment : entry.getValue().getFragments()) {
					fragments.add(fragment.string());
				}
				highLightMap.put(entry.getKey(), fragments);
			}
			queryRecord.setHighLight(highLightMap);
			queryRecord.setId(searchHit.getId());
			queryRecords.add(queryRecord);
		}
		queryResult.setRecords(queryRecords);
		return queryResult;
	}

	/**
	 * 短语匹配,多条件用or连接
	 * 
	 * @param requestBuilder
	 * @param queryPhrases
	 */
	private static QueryBuilder getQueryPhrase(final QueryPhrase queryPhrase) {
		if (queryPhrase == null) {
			return QueryBuilders.matchAllQuery();
		}
		// 批量查询对象
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 查询
		for (QueryMatch queryMatch : queryPhrase.getQueryMatch()) {
			// 短语匹配
			MatchPhraseQueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(queryMatch.getName(),
					queryMatch.getValue());
			if (queryPhrase.isAnd()) {
				// and
				boolQueryBuilder.must(queryBuilder);
			} else {
				// or
				boolQueryBuilder.should(queryBuilder);
			}
		}
		return boolQueryBuilder;
	}

	/**
	 * 设置高亮
	 * 
	 * @param requestBuilder
	 * @param queryHighlight
	 */
	private static void setQueryHighlight(final SearchRequestBuilder requestBuilder, int fragmentSize,
			final Collection<QueryHighlight> queryHighlights) {
		if (queryHighlights == null) {
			return;
		}
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.fragmentSize(fragmentSize);
		List<String> preTags = new ArrayList<String>();
		List<String> postTags = new ArrayList<String>();
		for (QueryHighlight queryHighlight : queryHighlights) {
			String fieldName = queryHighlight.getFieldName();
			String preTag = queryHighlight.getPreTag();
			String postTag = queryHighlight.getPostTag();
			if (fieldName != null) {
				highlightBuilder.field(fieldName);
				preTags.add(preTag);
				postTags.add(postTag);
			}
		}
		if (preTags.size() > 0) {
			highlightBuilder.preTags(preTags.toArray(new String[preTags.size()]));
		}
		if (postTags.size() > 0) {
			highlightBuilder.postTags(postTags.toArray(new String[postTags.size()]));
		}
		requestBuilder.highlighter(highlightBuilder);

	}

	/**
	 * 排序规则
	 * 
	 * @param requestBuilder
	 * @param sorts
	 */
	private static void setQuerySort(final SearchRequestBuilder requestBuilder, Collection<QuerySort> sorts) {
		if (sorts == null) {
			return;
		}
		for (QuerySort sort : sorts) {
			requestBuilder.addSort(sort.getField(), sort.getOrder());
		}
	}

	/**
	 * 设置查询的限制条件 , 用于分页与限制最大耗时
	 * 
	 * @param requestBuilder
	 * @param queryLimit
	 */
	private static void setQueryLimit(final SearchRequestBuilder requestBuilder, final QueryLimit queryLimit) {
		if (queryLimit == null) {
			return;
		}
		Integer from = queryLimit.getFrom();
		Integer size = queryLimit.getSize();
		Long timeout = queryLimit.getTimeout();
		// 开始条数
		if (from != null) {
			requestBuilder.setFrom(from);
		}
		// 记录数
		if (size != null) {
			requestBuilder.setSize(size);
		}
		// 查询时间
		if (timeout != null) {
			// 检索的最长时间
			requestBuilder.setTimeout(new TimeValue(timeout));
		}

	}

	/**
	 * 转换为map对象
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> toMap(Object source) {
		Map<String, Object> m = null;
		if (source instanceof Map) {
			m = (Map<String, Object>) source;
		} else {
			m = ObjectUtil.toMap(source);
			// 不需要类名
			if (m.containsKey("class")) {
				m.remove("class");
			}
		}
		return m;
	}

	/**
	 * 执行并返回结果集
	 * 
	 * @param bulkRequest
	 * @return
	 */
	private static LinkedHashMap<String, String> executeBulkRequestBuilder(final BulkRequestBuilder bulkRequest) {
		LinkedHashMap<String, String> result = new LinkedHashMap<>();
		for (BulkItemResponse response : bulkRequest.get().getItems()) {
			if (response.isFailed()) {
				result.put(response.getId(), response.getFailureMessage());
			} else {
				result.put(response.getId(), null);
			}
		}
		return result;
	}

}
