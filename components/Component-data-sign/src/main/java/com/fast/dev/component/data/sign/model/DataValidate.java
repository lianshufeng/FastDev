package com.fast.dev.component.data.sign.model;

import java.io.Serializable;

/**
 * 数据校验配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月5日
 *
 */
public class DataValidate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 超时时间
	private long timeOut = 1000 * 60 * 60 * 24;

	/**
	 * @return the timeOut
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

}
