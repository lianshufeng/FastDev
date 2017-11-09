package com.fast.dev.component.data.sign.service;

import javax.servlet.http.HttpServletRequest;

import com.fast.dev.component.data.sign.model.ValidateSecretToken;

/**
 * 数据校验接口
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月6日
 *
 */
public interface DataValidateManager {

	/**
	 * 设置当前会话中使用的校验令牌
	 * 
	 * @param secretToken
	 */
	public ValidateSecretToken secretToken(HttpServletRequest request);

}
