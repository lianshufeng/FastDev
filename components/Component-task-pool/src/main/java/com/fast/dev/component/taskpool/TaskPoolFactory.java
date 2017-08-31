package com.fast.dev.component.taskpool;

import com.fast.dev.component.taskpool.model.TaskPoolConfig;
import com.fast.dev.component.taskpool.pool.TaskManager;
import com.fast.dev.component.taskpool.pool.TaskManagerImpl;

/**
 * 任务池
 * 
 * @名称 ClusterFactory.java
 * @作者 练书锋
 * @时间 2017年8月30日
 *
 */
public class TaskPoolFactory {

	/**
	 * 创建配置
	 * 
	 * @param taskPoolConfig
	 * @return
	 */
	public static TaskManager create(TaskPoolConfig taskPoolConfig) {
		TaskManagerImpl taskManager = new TaskManagerImpl();
		taskManager.setTaskPoolConfig(taskPoolConfig);
		// 初始化
		taskManager.init();
		return taskManager;
	}

}
