package com.fast.dev.component.webservice.impl.server;

/**
 * 安全回调句柄
 * 
 * @author 练书锋
 *
 */
public interface WebServiceSecurityHandle {

	/**
	 * 根据应用令牌，获取相应的安全令牌
	 * 
	 * @param ApplicationToken
	 * @return
	 */
	public String getSecurityToken(String ApplicationToken);

}
