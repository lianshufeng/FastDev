package com.fast.dev.crawler.model;

/**
 * 列表爬虫参数
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public class ListCrawlerParameter extends CrawlerParameter {
	// 结束标识
	private String endInfo;

	// 下一页地址
	private String nextPage;

	/**
	 * @return the endInfo
	 */
	public String getEndInfo() {
		return endInfo;
	}

	/**
	 * @param endInfo
	 *            the endInfo to set
	 */
	public void setEndInfo(String endInfo) {
		this.endInfo = endInfo;
	}

	/**
	 * @return the nextPage
	 */
	public String getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage
	 *            the nextPage to set
	 */
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public ListCrawlerParameter() {
		// TODO Auto-generated constructor stub
	}

	public ListCrawlerParameter(String endInfo, String nextPage) {
		super();
		this.endInfo = endInfo;
		this.nextPage = nextPage;
	}

}
