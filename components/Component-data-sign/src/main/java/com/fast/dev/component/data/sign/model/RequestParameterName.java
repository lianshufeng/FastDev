package com.fast.dev.component.data.sign.model;

import java.io.Serializable;

/**
 * 请求参数配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月5日
 *
 */
public class RequestParameterName implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 请求参数的的校验码参数名称
	private String requestHashName = "_hash";

	// 请求参数的时间参数名称
	private String requestTimeName = "_time";

	// 排除的请求名称，排除外的参数名不会做数据校验
	private String[] excludeRequestName = new String[] { "callback" };

	/**
	 * @return the requestHashName
	 */
	public String getRequestHashName() {
		return requestHashName;
	}

	/**
	 * @param requestHashName
	 *            the requestHashName to set
	 */
	public void setRequestHashName(String requestHashName) {
		this.requestHashName = requestHashName;
	}

	/**
	 * @return the requestTimeName
	 */
	public String getRequestTimeName() {
		return requestTimeName;
	}

	/**
	 * @param requestTimeName
	 *            the requestTimeName to set
	 */
	public void setRequestTimeName(String requestTimeName) {
		this.requestTimeName = requestTimeName;
	}

	/**
	 * @return the excludeRequestName
	 */
	public String[] getExcludeRequestName() {
		return excludeRequestName;
	}

	/**
	 * @param excludeRequestName
	 *            the excludeRequestName to set
	 */
	public void setExcludeRequestName(String[] excludeRequestName) {
		this.excludeRequestName = excludeRequestName;
	}

}
