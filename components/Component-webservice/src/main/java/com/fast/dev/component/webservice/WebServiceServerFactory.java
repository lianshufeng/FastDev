package com.fast.dev.component.webservice;

import org.springframework.remoting.caucho.HessianServiceExporter;

import com.fast.dev.component.webservice.impl.server.HessianServiceExporterSecurity;
import com.fast.dev.component.webservice.impl.server.WebServiceSecurityHandle;

/**
 * Hessian的服务端创建工厂类
 * 
 * @作者 练书锋
 * @时间 2015年10月8日
 *
 */

public class WebServiceServerFactory {

	/**
	 * 创建HessianService的服务端,
	 * 
	 * 使用方法： 配置类上使用注解 @Configuration ， 使用 @Bean(name = "/remote/service") 描述远程接口
	 * 
	 * @param serviceInterface
	 * @param t
	 * @return
	 */
	public static <T> HessianServiceExporter create(Class<T> serviceInterface, T t) {
		return create(serviceInterface, t, null);
	}

	/**
	 * 创建HessianService的服务端,
	 * 
	 * 使用方法： 配置类上使用注解 @Configuration ， 使用 @Bean(name = "/remote/service") 描述远程接口
	 * 
	 * @param serviceInterface
	 * @param t
	 * @param webServiceSecurityHandle
	 *            获取安全令牌，必须与配套的客户端使用
	 * @return
	 */
	public static <T> HessianServiceExporter create(Class<T> serviceInterface, T t,
			WebServiceSecurityHandle webServiceSecurityHandle) {
		HessianServiceExporterSecurity hessianServiceExporter = new HessianServiceExporterSecurity();
		hessianServiceExporter.setServiceInterface(serviceInterface);
		hessianServiceExporter.setService(t);
		hessianServiceExporter.setSecurityHandle(webServiceSecurityHandle);
		return hessianServiceExporter;
	}

}
