package com.fast.dev.es.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fast.dev.es.dao.ESDao;

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
	public String[] save(Serializable... sources) {
		if (sources == null) {
			return null;
		}
		List<String> result = new ArrayList<>();
		for (Serializable obj : sources) {
			IndexRequestBuilder indexRequestBuilder = indexRequestBuilder();
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

	/**
	 * 取出请求创建
	 * 
	 * @return
	 */
	private IndexRequestBuilder indexRequestBuilder() {
		return this.client.prepareIndex(index, type);
	}

	@Override
	public boolean update(String id, Serializable source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Map<String, Serializable> sources) {
		// TODO Auto-generated method stub
		return false;
	}

}
