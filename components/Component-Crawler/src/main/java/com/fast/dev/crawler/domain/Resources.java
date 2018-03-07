package com.fast.dev.crawler.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

@Document
public class Resources extends SuperEntity {

	// 标题
	private String title;
	// 连接
	@Indexed(unique = true)
	private String url;

	// 发布时间
	@Indexed
	private long publishTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

}
