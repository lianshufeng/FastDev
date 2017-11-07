package com.fast.dev.component.data.sign.model;

import java.io.Serializable;

/**
 * 数据签名配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月5日
 *
 */
public class DataSignConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 请求的url
	private RequestUrl url = new RequestUrl();

	// 请求的参数
	private RequestParameterName parameter = new RequestParameterName();

	// 校验规则
	private DataValidate validate = new DataValidate();

	/**
	 * @return the url
	 */
	public RequestUrl getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(RequestUrl url) {
		this.url = url;
	}

	/**
	 * @return the parameter
	 */
	public RequestParameterName getParameter() {
		return parameter;
	}

	/**
	 * @param parameter
	 *            the parameter to set
	 */
	public void setParameter(RequestParameterName parameter) {
		this.parameter = parameter;
	}

	/**
	 * @return the validate
	 */
	public DataValidate getValidate() {
		return validate;
	}

	/**
	 * @param validate
	 *            the validate to set
	 */
	public void setValidate(DataValidate validate) {
		this.validate = validate;
	}

}
