package com.fast.dev.crawler.model;

/**
 * 内容详细页面
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public class ContentResult extends CrawlerResult {

	// 标题
	private String title;

	// 创建时间
	private long publishTime;

	// 需要访问详情的地址
	private String[] urls;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	public long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

	public ContentResult() {
		// TODO Auto-generated constructor stub
	}

	public ContentResult(String title, long publishTime, String... urls) {
		super();
		this.title = title;
		this.publishTime = publishTime;
		this.urls = urls;
	}

}
