package com.fast.dev.es.query;

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
	private QueryRecord[] records;

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

	/**
	 * @return the records
	 */
	public QueryRecord[] getRecords() {
		return records;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(QueryRecord[] records) {
		this.records = records;
	}

}
