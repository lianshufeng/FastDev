package com.fast.dev.es.query;

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
	private Map<String, String[]> highLight;

	/**
	 * @return the source
	 */
	public Map<String, Object> getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(Map<String, Object> source) {
		this.source = source;
	}

	/**
	 * @return the highLight
	 */
	public Map<String, String[]> getHighLight() {
		return highLight;
	}

	/**
	 * @param highLight
	 *            the highLight to set
	 */
	public void setHighLight(Map<String, String[]> highLight) {
		this.highLight = highLight;
	}

}
