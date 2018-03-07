package com.fast.dev.crawler.model;

/**
 * 列表页面返回的URL结果集
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public class ListUrlsResult extends ListCrawlerResult {
	// 需要访问详情的地址
	private String[] urls;
	// 结束标识
		private String endInfo;

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
		public ListUrlsResult setEndInfo(String endInfo) {
			this.endInfo = endInfo;
			return this;
		}

	/**
	 * @return the urls
	 */
	public String[] getUrls() {
		return urls;
	}

	/**
	 * @param urls
	 *            the urls to set
	 */
	public ListUrlsResult setUrls(String[] urls) {
		this.urls = urls;
		return this;
	}


}
