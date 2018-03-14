package com.fast.dev.es.conf;

/**
 * ES连接客户端配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */
public class ESConfig {

	// 数组 格式为 ip:端口
	private String[] hosts;

	/**
	 * @return the hosts
	 */
	public String[] getHosts() {
		return hosts;
	}

	/**
	 * @param hosts
	 *            the hosts to set
	 */
	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

}
