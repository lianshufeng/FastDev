package com.fast.dev.example.security.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.fast.dev.component.user.security.model.UserRole;
import com.fast.dev.component.user.security.service.UserSecurityService;
import com.fast.dev.example.security.model.UserInfo;

@Component
public class UserSecurityServiceImpl implements UserSecurityService<UserInfo> {

	/**
	 * 支持session与noSesison ，需要指定会话标识
	 */
	@Override
	public String session(HttpServletRequest httpServletRequest) {
		String uToken = httpServletRequest.getParameter("_uToken");
		return uToken;
	}

	@Override
	public UserRole<?> role(String sessionId) {
		System.out.println("sessionId : " + sessionId);
		UserRole<UserInfo> userRole = new UserRole<UserInfo>();
		userRole.setUser(new UserInfo("xiaofeng", 28));
		userRole.setUserId("TestUserId");
		userRole.setRoleNames(new String[] { "ROLE_user", "ROLE_default" });
		return userRole;
	}

}
