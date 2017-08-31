package com.fast.dev.component.taskpool.pool;

import com.fast.dev.component.taskpool.model.TaskPoolConfig;

/**
 * 集群任务管理器， 自动内存与持久化数据层
 * 
 * @作者 练书锋
 * @时间 2016年7月14日
 *
 */
public abstract class TaskManager {

	// 保存的配置
	protected TaskPoolConfig taskPoolConfig;

	public TaskPoolConfig getTaskPoolConfig() {
		return taskPoolConfig;
	}

	public void setTaskPoolConfig(TaskPoolConfig taskPoolConfig) {
		this.taskPoolConfig = taskPoolConfig;
	}

	/**
	 * 执行任务
	 */
	public abstract void executeTask(TaskParameter taskParameter);

	/**
	 * 销毁
	 */
	public abstract void shutdown();

}
