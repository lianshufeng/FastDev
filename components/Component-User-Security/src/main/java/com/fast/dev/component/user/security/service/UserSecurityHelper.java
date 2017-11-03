package com.fast.dev.component.user.security.service;

import com.fast.dev.component.user.security.token.UserToken;

/**
 * 用户权限助手, 可以在当前线程中取出已授权的用户信息
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
public interface UserSecurityHelper {
	/**
	 * 取出当前线程中用户
	 * 
	 * @return
	 */
	public <T> UserToken<T> get();

}
