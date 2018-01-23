package com.fast.dev.component.mongodb.lock.config;

import com.fast.dev.core.lock.config.LockOption;

/**
 * 远程锁配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月20日
 *
 */
public class RemoteLockOption extends LockOption {
	// 最大存活时间
	private long maxLiveTime = 1000 * 60 * 5;

	/**
	 * @return the maxLiveTime
	 */
	public long getMaxLiveTime() {
		return maxLiveTime;
	}

	/**
	 * @param maxLiveTime
	 *            the maxLiveTime to set
	 */
	public void setMaxLiveTime(long maxLiveTime) {
		this.maxLiveTime = maxLiveTime;
	}

}
