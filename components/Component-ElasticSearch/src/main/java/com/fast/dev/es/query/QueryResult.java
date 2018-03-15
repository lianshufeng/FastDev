package com.fast.dev.es.query;

import java.util.Collection;

/**
 * 查询结果集
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryResult {
	// 总数
	private long total;

	// 查询记录
	private Collection<QueryRecord> records;

	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	public Collection<QueryRecord> getRecords() {
		return records;
	}

	public void setRecords(Collection<QueryRecord> records) {
		this.records = records;
	}

	public QueryResult() {
		// TODO Auto-generated constructor stub
	}

	public QueryResult(long total, Collection<QueryRecord> records) {
		super();
		this.total = total;
		this.records = records;
	}

}
