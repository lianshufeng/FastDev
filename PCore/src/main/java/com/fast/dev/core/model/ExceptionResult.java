package com.fast.dev.core.model;

public class ExceptionResult<T> extends InvokerResult<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 异常
	private ExceptionModel exception;

	public ExceptionModel getException() {
		return exception;
	}

	public void setException(ExceptionModel exception) {
		this.exception = exception;
	}
	
	
	
	

}
