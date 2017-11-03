package com.fast.dev.component.user.security.model;

import java.io.Serializable;

/**
 * 用户信息与角色
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
public class UserRole<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户ID
	private String userId;

	// 用户信息
	private T user;

	// 角色名
	private String[] roleNames;

	/**
	 * @return the user
	 */
	public T getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(T user) {
		this.user = user;
	}

	/**
	 * @return the roleNames
	 */
	public String[] getRoleNames() {
		return roleNames;
	}

	/**
	 * @param roleNames
	 *            the roleNames to set
	 */
	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
