package com.fast.dev.core.lock.config;

/**
 * 远程锁配置对象
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月17日
 *
 */
public abstract class LockOption {

	// 业务名
	private String serviceName = "_default_";

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName
	 *            the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
