package com.fast.dev.user.security.model;

import java.io.Serializable;

/**
 * 用户权限配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
public class UserSecurityConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 角色的前缀名
	private String rolePrefixName = "ROLE_";

	// 用户缓存
	private UserCacheConfig userCache = new UserCacheConfig(60, 60, 1000, false);
	
	// 需要拦截方法的URL
	private String[] needSecurityMethodUrl = null;

	// 在拦截的URL中排除不拦截的URL
	private String[] excludeSecurityMethodUrl = null;

	// 执行拦截URL的优先级
	private int SecurityMethodUrlLevel = 0;

	/**
	 * @return the rolePrefixName
	 */
	public String getRolePrefixName() {
		return rolePrefixName;
	}

	/**
	 * @param rolePrefixName
	 *            the rolePrefixName to set
	 */
	public void setRolePrefixName(String rolePrefixName) {
		this.rolePrefixName = rolePrefixName;
	}


	/**
	 * @return the needSecurityMethodUrl
	 */
	public String[] getNeedSecurityMethodUrl() {
		return needSecurityMethodUrl;
	}

	/**
	 * @param needSecurityMethodUrl
	 *            the needSecurityMethodUrl to set
	 */
	public void setNeedSecurityMethodUrl(String[] needSecurityMethodUrl) {
		this.needSecurityMethodUrl = needSecurityMethodUrl;
	}

	/**
	 * @return the excludeSecurityMethodUrl
	 */
	public String[] getExcludeSecurityMethodUrl() {
		return excludeSecurityMethodUrl;
	}

	/**
	 * @param excludeSecurityMethodUrl
	 *            the excludeSecurityMethodUrl to set
	 */
	public void setExcludeSecurityMethodUrl(String[] excludeSecurityMethodUrl) {
		this.excludeSecurityMethodUrl = excludeSecurityMethodUrl;
	}

	/**
	 * @return the securityMethodUrlLevel
	 */
	public int getSecurityMethodUrlLevel() {
		return SecurityMethodUrlLevel;
	}

	/**
	 * @param securityMethodUrlLevel
	 *            the securityMethodUrlLevel to set
	 */
	public void setSecurityMethodUrlLevel(int securityMethodUrlLevel) {
		SecurityMethodUrlLevel = securityMethodUrlLevel;
	}

	/**
	 * @return the userCache
	 */
	public UserCacheConfig getUserCache() {
		return userCache;
	}

	/**
	 * @param userCache the userCache to set
	 */
	public void setUserCache(UserCacheConfig userCache) {
		this.userCache = userCache;
	}
	
	
	

}
