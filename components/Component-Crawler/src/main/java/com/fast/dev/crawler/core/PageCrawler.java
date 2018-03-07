package com.fast.dev.crawler.core;

/**
 * 页面爬虫
 * 
 * @作者 练书锋
 * @时间 2018年3月7日
 *
 *
 */
public interface PageCrawler extends Crawler {

	/**
	 * 所有的页面
	 * 
	 * @return
	 */
	public abstract String[] pageUrls();

	/**
	 * 重复的页面数量
	 * 
	 * @return
	 */
	public abstract int repeatPageCount();

}
