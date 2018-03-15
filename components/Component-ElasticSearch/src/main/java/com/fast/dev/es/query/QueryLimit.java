package com.fast.dev.es.query;

/**
 * 
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryLimit {

	// 查询数量
	private Integer size;
	// 查询开始记录数
	private Integer from;
	// 查询最大时常
	private Long timeout;

	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * @return the from
	 */
	public Integer getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Integer from) {
		this.from = from;
	}

	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

}
