package com.fast.dev.component.user.security.token;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 角色模型
 * 
 * @author 练书锋
 *
 */
public class UserToken<T> extends AbstractAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// sessionId
	private String sessionId;

	// 用户id
	private String userId;

	// 创建时间,可以参考缓存
	private long createTime;

	// 用户信息
	private T user;

	// 角色名
	private String[] roleNames;

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

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

	public UserToken(Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

}
