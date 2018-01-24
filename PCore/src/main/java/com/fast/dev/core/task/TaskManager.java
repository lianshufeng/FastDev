package com.fast.dev.core.task;

import com.fast.dev.core.task.model.Task;

/**
 * 任务管理器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
public interface TaskManager {

	/**
	 * 执行任务
	 */
	public <T> boolean execute(Class<? extends Task<T>> taskClass, T data);

	/**
	 * 执行任务
	 * 
	 * @param taskName任务名
	 * @param taskClass
	 * @param data
	 * @return
	 */
	public <T> boolean execute(String taskName, Class<? extends Task<T>> taskClass, T data);

	/**
	 * 销毁任务
	 */
	public void shutdown();

}
