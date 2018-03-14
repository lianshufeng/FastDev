package com.fast.dev.es.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PreDestroy;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fast.dev.es.dao.ESDao;
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
	public boolean save(String id, Serializable source) {
		if (StringUtils.isEmpty(id) || source == null) {
			return false;
		}
		IndexResponse indexResponse = this.client.prepareIndex(index, type, id).setSource(toMap(source)).get();
		return id.equals(indexResponse.getId());
	}

	@Override
	public String[] save(Serializable... sources) {
		if (sources == null) {
			return null;
		}
		// 用于接收插入的数据id
		List<String> result = new ArrayList<>();
		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (Serializable source : sources) {
			IndexRequestBuilder request = this.client.prepareIndex(index, type).setSource(toMap(source));
			bulkRequest.add(request);
		}
		BulkResponse bulkResponse = bulkRequest.get();
		for (BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
			result.add(bulkItemResponse.getId());
		}
		return result.toArray(new String[result.size()]);
	}

	@Override
	public Map<String, ?>[] get(String... id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String... id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean update(final String id, final Serializable source) {
		if (StringUtils.isEmpty(id) || source == null) {
			return false;
		}
		Map<String, String> result = this.update(new HashMap<String, Serializable>() {
			private static final long serialVersionUID = 1L;
			{
				put(id, source);
			}
		});
		return result.containsKey(id) && result.get(id) == null;
	}

	@Override
	public Map<String, String> update(Map<String, Serializable> sources) {
		if (sources == null || sources.size() == 0) {
			return null;
		}
		BulkRequestBuilder bulkRequest = this.client.prepareBulk();
		for (Entry<String, Serializable> entry : sources.entrySet()) {
			String id = entry.getKey();
			Serializable obj = entry.getValue();
			// 创建批量请求对象
			UpdateRequestBuilder requestBuilder = this.client.prepareUpdate(index, type, id);
			requestBuilder.setDoc(toMap(obj));
			bulkRequest.add(requestBuilder);
		}
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

	/**
	 * 转换为map对象
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> toMap(Serializable source) {
		Map<String, Object> m = null;
		if (source instanceof Map) {
			m = (Map<String, Object>) source;
		} else {
			m = ObjectUtil.toMap(source);
		}
		return m;
	}

}
