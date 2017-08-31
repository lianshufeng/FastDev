package com.fast.dev.component.webservice;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.fast.dev.component.webservice.impl.client.HessianURLConnectionFactorySecurity;

/**
 * hessian的客户端工厂方法
 * 
 * @作者 练书锋
 * @时间 2015年10月8日
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class WebServiceClientFactory {

	/**
	 * 创建客户端
	 * 
	 * @param serviceInterface
	 *            接口
	 * @param url
	 *            URL
	 * @return
	 */
	public static <T> T create(Class<T> serviceInterface, String url) {
		return create(serviceInterface, url, null, null);
	}

	/**
	 * 创建接口对应的客户端
	 * 
	 * @param serviceInterface
	 *            接口
	 * @param url
	 *            webservice的连接
	 * @param aToken
	 *            应用令牌
	 * @param sToken
	 *            安全令牌
	 * @return
	 */
	public static <T> T create(Class<T> serviceInterface, String url, String aToken, String sToken) {
		HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
		// 创建自定义对象
		HessianURLConnectionFactorySecurity connectionFactory = new HessianURLConnectionFactorySecurity();
		// 设置令牌
		connectionFactory.setaToken(aToken);
		connectionFactory.setsToken(sToken);
		connectionFactory.setHessianProxyFactory(hessianProxyFactory);
		hessianProxyFactory.setConnectionFactory(connectionFactory);
		T service = null;
		try {
			service = ((T) hessianProxyFactory.create(serviceInterface, url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return service;
	}
}
