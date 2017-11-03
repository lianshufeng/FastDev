package com.fast.dev.component.user.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fast.dev.component.user.security.model.UserCacheConfig;
import com.fast.dev.component.user.security.model.UserRole;
import com.fast.dev.component.user.security.model.UserSecurityConfig;
import com.fast.dev.component.user.security.service.UserSecurityHelper;
import com.fast.dev.component.user.security.service.UserSecurityService;
import com.fast.dev.component.user.security.token.UserToken;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 具体用户身份认真的实现
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserSecurityAuthenticationManager implements UserSecurityHelper {

	// 缓存管理器
	private CacheManager cacheManager = CacheManager.create();
	// 用户缓存
	private Cache userCache = null;
	
	@Resource
	private UserSecurityConfig userSecurityConfig;

	@Resource
	private UserSecurityService<?> userSecurityService;

	/**
	 * 由拦截器触发，在访问权限方法之前
	 * 
	 * @param request
	 */

	public void preHandle(HttpServletRequest request) {
		// 初始化权限
		setAuthentication(null);
		// 取出sessionId
		String sessionId = this.userSecurityService.session(request);
		if (StringUtils.isEmpty(sessionId)) {
			return;
		}
		UserRole<?> userRole = role(sessionId);
		if (StringUtils.isEmpty(userRole)) {
			return;
		}

		// 远程信息
		String remoteHost = request.getRemoteHost();
		UserToken<?> userToken = createUserToken(sessionId, remoteHost, userRole);
		// 设置权限
		setAuthentication(userToken);
	}

	/**
	 * 由拦截器触发，在访问权限方法之后
	 * 
	 * @param request
	 */
	public void afterCompletion(HttpServletRequest request) {
		// 清空权限
		setAuthentication(null);
	}

	@Override
	public <T> UserToken<T> get(Class<T> cls) {
		return (UserToken<T>) SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 配置角色
	 * 
	 * @param sessionId
	 */
	private UserRole<?> role(final String sessionId) {
		UserRole<?> userRole = null;
		Element element = userCache.get(sessionId);
		if (element != null) {
			userRole = (UserRole<?>) element.getObjectValue();
		} else {
			userRole = this.userSecurityService.role(sessionId);
			userCache.put(new Element(sessionId, userRole));
		}
		return userRole;
	}

	@PostConstruct
	private void initCache() {
		UserCacheConfig cacheConfig = userSecurityConfig.getUserCache();
		userCache = new Cache("UserCacheCollection", cacheConfig.getMaxMemoryCount(), cacheConfig.isOverflowToDisk(),
				false, cacheConfig.getTimeToLiveSeconds(), cacheConfig.getTimeToIdleSeconds());
		cacheManager.addCache(userCache);
	}

	@PreDestroy
	private void shutdown() {
		cacheManager.shutdown();
	}

	/**
	 * 设置当前访问权限
	 * 
	 * @param authentication
	 */
	private void setAuthentication(Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 创建用户令牌
	 * 
	 * @param sessionId
	 * @param remoteHost
	 * @param userRole
	 * @return
	 */
	private UserToken createUserToken(String sessionId, String remoteHost, UserRole<?> userRole) {
		// 权限
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String roleName : userRole.getRoleNames()) {
			authorities.add(new SimpleGrantedAuthority(roleName));
		}

		UserToken userToken = new UserToken(authorities);
		// 对象创建时间
		userToken.setCreateTime(System.currentTimeMillis());
		// session
		userToken.setSessionId(sessionId);
		// 拷贝属性
		BeanUtils.copyProperties(userRole, userToken);
		// 客户端信息
		userToken.setDetails(remoteHost);
		// 通过认真
		userToken.setAuthenticated(true);

		return userToken;
	}

}
