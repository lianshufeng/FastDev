package com.fast.dev.core.task.impl.single;

import java.util.TimerTask;

import com.fast.dev.core.task.model.Task;

/**
 * 
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
public class SleepTask<T> extends TimerTask {

	// 任务管理器
	private SingleTaskManager singleTaskManager;

	// 执行的数据
	private T data;

	// 任务
	private Class<? extends Task<T>> taskClass;

	/**
	 * @return the singleTaskManager
	 */
	public SingleTaskManager getSingleTaskManager() {
		return singleTaskManager;
	}

	/**
	 * @param singleTaskManager
	 *            the singleTaskManager to set
	 */
	public void setSingleTaskManager(SingleTaskManager singleTaskManager) {
		this.singleTaskManager = singleTaskManager;
	}

	/**
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return the taskClass
	 */
	public Class<? extends Task<T>> getTaskClass() {
		return taskClass;
	}

	/**
	 * @param taskClass
	 *            the taskClass to set
	 */
	public void setTaskClass(Class<? extends Task<T>> taskClass) {
		this.taskClass = taskClass;
	}

	@Override
	public void run() {
		this.singleTaskManager.executeThread(this.taskClass, data);
	}

}
