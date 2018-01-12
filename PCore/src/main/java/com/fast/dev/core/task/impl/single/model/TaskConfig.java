package com.fast.dev.core.task.impl.single.model;

/**
 * 
 * 任务配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
public class TaskConfig {

	// 最低休眠时间
	private long maxSleepTime = 1000l;

	// 线程池最大并发任务
	private int maxThreadCount = 30;

	/**
	 * @return the maxSleepTime
	 */
	public long getMaxSleepTime() {
		return maxSleepTime;
	}

	/**
	 * @param maxSleepTime
	 *            the maxSleepTime to set
	 */
	public void setMaxSleepTime(long maxSleepTime) {
		this.maxSleepTime = maxSleepTime;
	}

	/**
	 * @return the maxThreadCount
	 */
	public int getMaxThreadCount() {
		return maxThreadCount;
	}

	/**
	 * @param maxThreadCount
	 *            the maxThreadCount to set
	 */
	public void setMaxThreadCount(int maxThreadCount) {
		this.maxThreadCount = maxThreadCount;
	}

}