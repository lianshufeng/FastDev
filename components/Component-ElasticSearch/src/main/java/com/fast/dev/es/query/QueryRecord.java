package com.fast.dev.es.query;

import java.util.Collection;
import java.util.Map;

/**
 * 查询的单条记录
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryRecord {
	// 数据源
	private Map<String, Object> source;
	// 高亮对象
	private Map<String, Collection<String>> highLight;

	public Map<String, Object> getSource() {
		return source;
	}

	public void setSource(Map<String, Object> source) {
		this.source = source;
	}

	public Map<String, Collection<String>> getHighLight() {
		return highLight;
	}

	public void setHighLight(Map<String, Collection<String>> highLight) {
		this.highLight = highLight;
	}

	public QueryRecord() {
		// TODO Auto-generated constructor stub
	}

	public QueryRecord(Map<String, Object> source, Map<String, Collection<String>> highLight) {
		super();
		this.source = source;
		this.highLight = highLight;
	}

}
