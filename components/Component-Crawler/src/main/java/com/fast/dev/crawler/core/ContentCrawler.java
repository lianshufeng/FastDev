package com.fast.dev.crawler.core;

import com.fast.dev.crawler.model.ContentResult;
import com.fast.dev.crawler.model.CrawlerParameter;

/**
 * 内容爬虫
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public interface ContentCrawler extends Crawler {

	/**
	 * 调用内容爬虫
	 */
	public abstract ContentResult call(CrawlerParameter parameter);

}
