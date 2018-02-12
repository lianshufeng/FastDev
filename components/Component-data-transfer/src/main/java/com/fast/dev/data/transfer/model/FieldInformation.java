package com.fast.dev.data.transfer.model;

/**
 * 字段信息的描述
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public class FieldInformation {

	// 字段名称
	private String name;

	// 字段类型
	private Class<?> type;

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
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	public FieldInformation() {
		// TODO Auto-generated constructor stub
	}

	public FieldInformation(String name, Class<?> type) {
		super();
		this.name = name;
		this.type = type;
	}

}
