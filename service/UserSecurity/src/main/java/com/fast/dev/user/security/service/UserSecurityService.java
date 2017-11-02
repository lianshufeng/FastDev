package com.fast.dev.user.security.service;

import javax.servlet.http.HttpServletRequest;

import com.fast.dev.user.security.model.UserRole;

/**
 * 用户缓存业务
 * 
 * @author 练书锋
 *
 */
public interface UserSecurityService<T> {

	/**
	 * 取出sessionId，本方法不会被缓存
	 * 
	 * @param httpServletRequest
	 * @return 请返回session的唯一标识
	 */
	public String session(HttpServletRequest httpServletRequest);

	/**
	 * 通过用户id取出用户的角色信息
	 * 
	 * @return 用户的角色
	 */
	public UserRole<?> role(String sessionId);

}
