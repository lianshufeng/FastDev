package com.fast.dev.data.transfer.model;

import java.util.List;

/**
 * 表集合
 * 
 * @author Administrator
 *
 */
public class TableItem {

	// 表名
	private String name;

	// 字段类型
	private List<String> fieldNames;

	// 数据
	private List<DataItem> dataItems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public List<DataItem> getDataItems() {
		return dataItems;
	}

	public void setDataItems(List<DataItem> dataItems) {
		this.dataItems = dataItems;
	}

}
