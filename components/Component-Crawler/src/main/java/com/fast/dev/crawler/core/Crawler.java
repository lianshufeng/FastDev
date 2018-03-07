package com.fast.dev.crawler.core;

/**
 * 
 * @作者 练书锋
 * @时间 2018年3月7日
 *
 *
 */
public interface Crawler {

	/**
	 * 调度器表达式
	 * 
	 * @return
	 */
	public abstract String corn();

	/**
	 * 任务名
	 * 
	 * @return
	 */
	public String taskName();

}
