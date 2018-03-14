package com.fast.dev.es.factory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 
 * ES 工厂类
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */

@SuppressWarnings("resource")
public class ESFactory {

	/**
	 * 创建客户端
	 * 
	 * @param hosts
	 * @return
	 */

	public static Client buildClient(String[] hosts) {
		List<TransportAddress> list = new ArrayList<>();
		for (String host : hosts) {
			InetSocketAddress address = null;
			String[] hostArr = host.split(":");
			if (hostArr.length > 1) {
				address = new InetSocketAddress(hostArr[0], Integer.parseInt(hostArr[1]));
			} else if (hostArr.length > 0) {
				address = new InetSocketAddress(hostArr[0], 9300);
			}
			if (address != null) {
				list.add(new TransportAddress(address));
			}
		}
		return new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(list.toArray(new TransportAddress[0]));
	}
	
	

}
