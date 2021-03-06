package com.fast.dev.crawler.core;

import java.util.List;

import com.fast.dev.crawler.model.UrlJob;

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
	public abstract List<UrlJob> pageUrls();

	/**
	 * 重复的页面数量
	 * 
	 * @return
	 */
	public abstract List<UrlJob> repeat(List<UrlJob> sources);

}
