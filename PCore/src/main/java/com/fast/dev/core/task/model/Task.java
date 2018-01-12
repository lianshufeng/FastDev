package com.fast.dev.core.task.model;

import java.io.Serializable;

/**
 * 任务配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
public abstract class Task<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 执行任务
	 * 
	 * @param data
	 */
	public abstract void run(T data);

}
