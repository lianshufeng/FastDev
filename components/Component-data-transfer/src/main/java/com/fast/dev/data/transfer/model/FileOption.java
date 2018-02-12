package com.fast.dev.data.transfer.model;

import java.util.List;

/**
 * 导出配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public class FileOption {

	// 版本号
	private String version;

	// 字段类型
	private List<FieldInformation> fields;

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the fields
	 */
	public List<FieldInformation> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<FieldInformation> fields) {
		this.fields = fields;
	}

}
