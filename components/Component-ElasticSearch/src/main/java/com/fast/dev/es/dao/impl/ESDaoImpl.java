package com.fast.dev.es.dao.impl;

import java.util.HashMap;
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
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.query.Sort;
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
	public Map<String, String> save(Object... sources) {
		if (sources == null) {
			return null;
		}
		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (Object source : sources) {
			IndexRequestBuilder request = this.client.prepareIndex(index, type).setSource(toMap(source));
			bulkRequest.add(request);
		}
		return executeBulkRequestBuilder(bulkRequest);
	}

	@Override
	public Map<String, Object> get(String... ids) {
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

	@Override
	public void list() {

		SearchRequestBuilder requestBuilder = this.client.prepareSearch(index);
		requestBuilder.setTypes(type);

	}

	public void list(Integer size, Integer from, Long timeout, Sort[] sorts) {
		SearchRequestBuilder requestBuilder = this.client.prepareSearch(index);
		requestBuilder.setTypes(type);
		// 检索方式
		requestBuilder.setSearchType(SearchType.DEFAULT);
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
		//排序规则
		if (sorts!=null) {
			for (Sort sort : sorts) {
				requestBuilder.addSort(sort.getField(), sort.getOrder());
			}
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
		}
		return m;
	}

	/**
	 * 执行并返回结果集
	 * 
	 * @param bulkRequest
	 * @return
	 */
	private static Map<String, String> executeBulkRequestBuilder(final BulkRequestBuilder bulkRequest) {
		Map<String, String> result = new HashMap<>();
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
