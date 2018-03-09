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

	// 需要访问详情的地址
	private String url;

	// 创建时间
	private long publishTime;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the publishTime
	 */
	public long getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTime
	 *            the publishTime to set
	 */
	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

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

	public ContentResult(String title, String url, long publishTime) {
		super();
		this.title = title;
		this.url = url;
		this.publishTime = publishTime;
	}

	public ContentResult() {
		// TODO Auto-generated constructor stub
	}

}
