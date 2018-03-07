package com.fast.dev.crawler.domain;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

public class UrlsEntity extends SuperEntity {
	// 任务名
	private String taskName;
	// 详情页的URL
	private String url;

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

}
