package com.fast.dev.crawler.core;

import com.fast.dev.crawler.model.ListCrawlerParameter;
import com.fast.dev.crawler.model.ListCrawlerResult;

/**
 * 接口抽象类
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public interface ListCrawler extends Crawler {

	/**
	 * 调度器表达式
	 * 
	 * @return
	 */
	public abstract String corn();

	/**
	 * 返回集合结果集
	 */
	public abstract ListCrawlerResult call(ListCrawlerParameter parameter);

}
