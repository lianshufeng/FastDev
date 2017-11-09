package com.fast.dev.component.user.security.conf;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.fast.dev.component.user.security.interceptors.UserSecurityConfigInterceptor;
import com.fast.dev.component.user.security.model.UserSecurityConfig;
import com.fast.dev.component.user.security.service.UserSecurityService;
import com.fast.dev.core.interceptor.UrlInterceptor;

/**
 * 配置spring安全
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月31日
 *
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

	static Logger logger = Logger.getLogger(SecurityConfiguration.class);


	/**
	 * 角色控制器
	 * 
	 * @return
	 */
	@Bean
	public RoleVoter roleVoter(UserSecurityConfig userSecurityConfig) {
		RoleVoter roleVoter = new RoleVoter();
		roleVoter.setRolePrefix(userSecurityConfig.getRolePrefixName());
		return roleVoter;
	}

	/**
	 * 用户方法的URL拦截器
	 * 
	 * @param userSecurityConfig
	 * @return
	 */
	@Bean
	public UrlInterceptor userSecurityConfigInterceptor(UserSecurityConfig userSecurityConfig) {
		return new UserSecurityConfigInterceptor(userSecurityConfig);
	}

	/**
	 * 设置权限认证成功也通知消息
	 */
	@Override
	public MethodInterceptor methodSecurityInterceptor() throws Exception {
		MethodInterceptor methodInterceptor = super.methodSecurityInterceptor();
		if (methodInterceptor instanceof MethodSecurityInterceptor) {
			MethodSecurityInterceptor methodSecurityInterceptor = (MethodSecurityInterceptor) methodInterceptor;
			methodSecurityInterceptor.setPublishAuthorizationSuccess(true);
		}
		return methodInterceptor;
	}

	/**
	 * 用户缓存接口
	 * 
	 * @param userSecurityService
	 * @return
	 */
	@Bean
	public UserSecurityService<?> userSecurityService(UserSecurityService<?> userSecurityService) {
		logger.info("UserSecurityService：" + userSecurityService);
		return userSecurityService;
	}

}
