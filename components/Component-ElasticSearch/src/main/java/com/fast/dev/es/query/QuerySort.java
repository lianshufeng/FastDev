package com.fast.dev.es.query;

import org.elasticsearch.search.sort.SortOrder;

/**
 * 排序条件
 * 
 * @作者 练书锋
 * @时间 2018年3月15日
 *
 *
 */
public class QuerySort {
	// 字段名
	private String field;
	// 排序条件
	private SortOrder order;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = order;
	}

	public QuerySort() {
		// TODO Auto-generated constructor stub
	}

	public QuerySort(String field, SortOrder order) {
		super();
		this.field = field;
		this.order = order;
	}

}
