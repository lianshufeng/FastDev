package com.fast.dev.core.model;

import java.io.Serializable;

import com.fast.dev.core.util.code.JsonUtil;

/**
 * 调用程序的结果
 * 
 * @作者 练书锋
 * @联系 oneday@vip.qq.com
 * @时间 2014年5月13日
 */
public class InvokerResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 执行结果
	 */
	private boolean success = false;
	/**
	 * 消息提示
	 */
	private T content;

	/**
	 * 使用时间
	 */
	private long usedTime;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		try {
			return JsonUtil.toJson(this);
		} catch (Exception e) {
		}
		return "{success : \"" + this.isSuccess() + "\" , content : \"" + content + "}";
	}

	public InvokerResult() {
		// TODO Auto-generated constructor stub
	}

	public InvokerResult(T content) {
		super();
		this.content = content;
		this.success = content != null;
	}

	public InvokerResult(boolean success, T content) {
		super();
		this.success = success;
		this.content = content;
	}

	public long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}

}
