package com.fast.dev.example.security.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.user.security.model.UserSecurityConfig;

/**
 * 配置用户权限
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
@Configuration
public class UserSecurityConfiguration {

	@Bean
	public UserSecurityConfig userSecurityConfig() throws Exception {
		return JsonUtil.loadToObject("UserSecurity.json", UserSecurityConfig.class);
	}

}
