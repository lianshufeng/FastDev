package com.fast.dev.crawler.model;

/**
 * 返回下页和列表
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public class ListNextPageAndUrlsResult extends ListUrlsResult {

	// 下页地址
	private String nextPage;

	// 访问下一页休息时间
	private long nextPageSleepTime = 1000;

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
	public ListNextPageAndUrlsResult setNextPage(String nextPage) {
		this.nextPage = nextPage;
		return this;
	}

	/**
	 * @return the nextPageSleepTime
	 */
	public long getNextPageSleepTime() {
		return nextPageSleepTime;
	}

	/**
	 * @param nextPageSleepTime
	 *            the nextPageSleepTime to set
	 */
	public ListNextPageAndUrlsResult setNextPageSleepTime(long nextPageSleepTime) {
		this.nextPageSleepTime = nextPageSleepTime;
		return this;
	}

	@Override
	public ListNextPageAndUrlsResult setUrls(String[] urls) {
		super.setUrls(urls);
		return this;
	}

	@Override
	public ListNextPageAndUrlsResult setEndInfo(String endInfo) {
		super.setEndInfo(endInfo);
		return this;
	}

}
