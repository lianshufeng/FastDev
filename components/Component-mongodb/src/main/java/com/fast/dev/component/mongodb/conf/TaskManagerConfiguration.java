package com.fast.dev.component.mongodb.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.core.task.TaskManager;
import com.fast.dev.core.task.impl.single.SingleTaskManager;
import com.fast.dev.core.task.impl.single.model.TaskConfig;

@Configuration
public class TaskManagerConfiguration {

	/**
	 * 单任务配置
	 * 
	 * @return
	 */
	@Bean("dBTimerHelperTaskManager")
	public TaskManager SingleTaskManager() {
		SingleTaskManager taskManager = new SingleTaskManager();
		TaskConfig taskConfig = new TaskConfig();
		taskConfig.setMaxSleepTime(60 * 60 * 1000);
		taskManager.config(taskConfig);
		return taskManager;
	}

}
