package com.fast.dev.component.baidu.speech.bean;

/**
 * 令牌容器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月7日
 *
 */
public class SpeechToken {

	// 访问令牌
	private String accessToken;

	// 超时时间戳
	private long timeoutStamp;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getTimeoutStamp() {
		return timeoutStamp;
	}

	public void setTimeoutStamp(long timeoutStamp) {
		this.timeoutStamp = timeoutStamp;
	}

	public SpeechToken(String accessToken, long timeoutStamp) {
		super();
		this.accessToken = accessToken;
		this.timeoutStamp = timeoutStamp;
	}

	public SpeechToken() {
		// TODO Auto-generated constructor stub
	}

}
