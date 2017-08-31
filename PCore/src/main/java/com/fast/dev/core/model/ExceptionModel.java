package com.fast.dev.core.model;

import java.io.Serializable;

/**
 * 异常模型
 * 
 * @author 练书锋
 *
 */
public class ExceptionModel implements Serializable {

	private static final long serialVersionUID = 1L;

	// 消息内容
	private String message;

	// 异常类名
	private String cls;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public ExceptionModel() {
		// TODO Auto-generated constructor stub
	}

	public ExceptionModel(String message, String cls) {
		super();
		this.message = message;
		this.cls = cls;
	}

}
