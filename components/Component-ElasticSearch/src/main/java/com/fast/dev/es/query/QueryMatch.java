package com.fast.dev.es.query;

/**
 * 匹配查询
 * 
 * @作者 练书锋
 * @时间 2018年3月16日
 *
 *
 */
public class QueryMatch {

	// 字段名
	private String name;
	// 匹配的值
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryMatch() {
		// TODO Auto-generated constructor stub
	}

	public QueryMatch(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

}
