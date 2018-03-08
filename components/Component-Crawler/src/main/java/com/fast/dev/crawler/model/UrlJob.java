package com.fast.dev.crawler.model;

import java.io.Serializable;
import java.util.Map;

/**
 * URL的任务
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月8日
 *
 */

public class UrlJob implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 地址
	private String url;

	// 数据集
	private Map<String, Object> data;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
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

	public UrlJob() {
		// TODO Auto-generated constructor stub
	}

	public UrlJob(String url, Map<String, Object> data) {
		super();
		this.url = url;
		this.data = data;
	}

	public UrlJob(String url) {
		super();
		this.url = url;
	}

}
