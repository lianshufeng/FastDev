package com.fast.dev.component.taskpool.pool.dao;

import java.util.List;

import com.fast.dev.component.taskpool.pool.TaskParameter;
import com.fast.dev.component.taskpool.pool.domain.TaskWork;

public interface TaskWorkDao {

	/**
	 * 添加任务
	 * 
	 * @param taskParameter
	 */
	public String addTask(TaskParameter taskParameter);

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 */
	public void removeTask(String taskId);

	/**
	 * 签名工作
	 * 
	 * @param taskId
	 */
	public boolean signInTask(String... taskId);

	/**
	 * 查找超时任务
	 * 
	 * @param timeOut
	 * @param size
	 * @return
	 */
	public List<TaskWork> loadTask(long timeOut, int maxCount);

}
