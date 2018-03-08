package com.fast.dev.crawler.domain;

import java.util.Map;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

public class UrlsEntity extends SuperEntity {
	// 任务名
	private String taskName;
	// 详情页的URL
	private String url;
	// 数据
	private Map<String, Object> data;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
