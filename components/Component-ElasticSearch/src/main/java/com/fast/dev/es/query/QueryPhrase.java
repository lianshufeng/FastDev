package com.fast.dev.es.query;

/**
 * 短语查询(模糊查询)
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryPhrase {

	// 字段名
	private String name;
	// 匹配的值
	private Object value;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

}
