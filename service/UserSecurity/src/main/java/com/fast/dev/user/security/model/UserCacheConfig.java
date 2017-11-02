package com.fast.dev.user.security.model;

import java.io.Serializable;

/**
 * 用户缓存设置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月2日
 *
 */
public class UserCacheConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// 总共缓存的时间（单位秒）
	private long timeToLiveSeconds;
	
	// 最后一次访问缓存的日期至失效之时的时间间隔（单位秒）
	private long timeToIdleSeconds;
	
	// 最大的缓存对象的数量
	private int maxMemoryCount;
	
	// 内存不够是否自动写到磁盘上
	private boolean overflowToDisk;

	/**
	 * @return the timeToLiveSeconds
	 */
	public long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	/**
	 * @param timeToLiveSeconds
	 *            the timeToLiveSeconds to set
	 */
	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	/**
	 * @return the timeToIdleSeconds
	 */
	public long getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	/**
	 * @param timeToIdleSeconds
	 *            the timeToIdleSeconds to set
	 */
	public void setTimeToIdleSeconds(long timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	/**
	 * @return the maxMemoryCount
	 */
	public int getMaxMemoryCount() {
		return maxMemoryCount;
	}

	/**
	 * @param maxMemoryCount
	 *            the maxMemoryCount to set
	 */
	public void setMaxMemoryCount(int maxMemoryCount) {
		this.maxMemoryCount = maxMemoryCount;
	}

	/**
	 * @return the overflowToDisk
	 */
	public boolean isOverflowToDisk() {
		return overflowToDisk;
	}

	/**
	 * @param overflowToDisk
	 *            the overflowToDisk to set
	 */
	public void setOverflowToDisk(boolean overflowToDisk) {
		this.overflowToDisk = overflowToDisk;
	}
	
	public UserCacheConfig() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param timeToLiveSeconds  总共缓存的时间（单位秒）
	 * @param timeToIdleSeconds  最后一次访问缓存的日期至失效之时的时间间隔（单位秒）
	 * @param maxMemoryCount  最大的缓存对象的数量
	 * @param overflowToDisk 内存不够是否自动写到磁盘上
	 */
	public UserCacheConfig(long timeToLiveSeconds, long timeToIdleSeconds, int maxMemoryCount, boolean overflowToDisk) {
		super();
		this.timeToLiveSeconds = timeToLiveSeconds;
		this.timeToIdleSeconds = timeToIdleSeconds;
		this.maxMemoryCount = maxMemoryCount;
		this.overflowToDisk = overflowToDisk;
	}
	
	

}
