package com.fast.dev.component.validate.service;

import com.fast.dev.component.validate.result.ValidateResult;

/**
 * 校验的功能接口
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年5月3日
 *
 */
public interface Validator {

	/**
	 * 校验方法
	 * 
	 * @param args
	 *            参数集合
	 * @param validateResult
	 *            设置重置对象将替换原方法执行
	 */
	public void validate(Object[] args, ValidateResult validateResult);

}
