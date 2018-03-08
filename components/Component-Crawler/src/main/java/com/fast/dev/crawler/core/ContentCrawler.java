package com.fast.dev.crawler.core;

import java.util.Map;

import com.fast.dev.crawler.model.ContentResult;

/**
 * 内容的详情
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public interface ContentCrawler extends Crawler {

	/**
	 * 调用爬虫获取标题与连接
	 * 
	 * @param url
	 * @return
	 */
	public abstract ContentResult call(String url, Map<String, Object> data);

}
