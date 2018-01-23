package com.fast.dev.component.remote.lock.config;

import com.fast.dev.core.lock.config.LockOption;

/**
 * 远程锁配置对象
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月17日
 *
 */
public class RemoteLockOption extends LockOption {

	// 会话超时时间
	private int sessionTimeout = 5000;

	// 最大线程阻塞的时间
	private long maxThreadWaitTime = 1000 * 60 * 60;

	// 主机名
	private String[] hostNames;

	/**
	 * @return the sessionTimeout
	 */
	public int getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param sessionTimeout
	 *            the sessionTimeout to set
	 */
	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	/**
	 * @return the hostNames
	 */
	public String[] getHostNames() {
		return hostNames;
	}

	/**
	 * @param hostNames
	 *            the hostNames to set
	 */
	public void setHostNames(String[] hostNames) {
		this.hostNames = hostNames;
	}

	/**
	 * @return the maxThreadWaitTime
	 */
	public long getMaxThreadWaitTime() {
		return maxThreadWaitTime;
	}

	/**
	 * @param maxThreadWaitTime
	 *            the maxThreadWaitTime to set
	 */
	public void setMaxThreadWaitTime(long maxThreadWaitTime) {
		this.maxThreadWaitTime = maxThreadWaitTime;
	}

	/**
	 * 获取主机连接字符串
	 * 
	 * @return
	 */
	public String getHostConnectString() {
		if (this.hostNames == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (String hostName : this.hostNames) {
			sb.append(hostName + ",");
		}
		return sb.toString();
	}

	public RemoteLockOption(String[] hostNames) {
		super();
		this.hostNames = hostNames;
	}

	public RemoteLockOption() {
		// TODO Auto-generated constructor stub
	}

}
