package com.fast.dev.component.data.sign.model;

/**
 * 校验令牌
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月6日
 *
 */
public class ValidateSecretToken {

	// 摘要令牌
	private String secretToken;

	// 时间戳
	private long timeStamp;

	/**
	 * @return the secretToken
	 */
	public String getSecretToken() {
		return secretToken;
	}

	/**
	 * @param secretToken
	 *            the secretToken to set
	 */
	public void setSecretToken(String secretToken) {
		this.secretToken = secretToken;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	public ValidateSecretToken() {
		// TODO Auto-generated constructor stub
	}

	public ValidateSecretToken(String secretToken, long timeStamp) {
		super();
		this.secretToken = secretToken;
		this.timeStamp = timeStamp;
	}
	
	
	
	
	

}
