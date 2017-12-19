package com.fast.dev.component.data.sign.excption;

import com.fast.dev.component.data.sign.type.DataValidateResult;

/**
 * 数据签名异常
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年12月19日
 *
 */
public class DataSignException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 数据签名的状态
	 */
	private DataValidateResult result;

	/**
	 * @return the result
	 */
	public DataValidateResult getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(DataValidateResult result) {
		this.result = result;
	}

	public DataSignException(DataValidateResult result) {
		super();
		this.result = result;
	}

	public DataSignException() {

	}

}
