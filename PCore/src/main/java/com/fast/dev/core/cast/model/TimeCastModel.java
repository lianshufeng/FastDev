package com.fast.dev.core.cast.model;

public class TimeCastModel {

	// 访问时间
	private long accessTime;

	// 访问对象
	private Object model;

	public long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(long accessTime) {
		this.accessTime = accessTime;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

}
