package com.fast.dev.crawler.core;

import java.util.List;
import java.util.Map;

import com.fast.dev.crawler.model.UrlJob;

/**
 * 每页的取出列表
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月7日
 *
 */
public interface ListCrawler extends Crawler {

	/**
	 * 
	 * @param pageUrl
	 *            每页的地址
	 * @return 返回内容页的地址
	 */
	public List<UrlJob> call(String url, Map<String, Object> data);

}
