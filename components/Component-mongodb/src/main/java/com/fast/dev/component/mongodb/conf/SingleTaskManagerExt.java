package com.fast.dev.component.mongodb.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.core.task.impl.single.SingleTaskManager;
import com.fast.dev.core.task.impl.single.model.TaskConfig;

@Component("dBTimerHelperTaskManager")
public class SingleTaskManagerExt extends SingleTaskManager {

	@Autowired
	private void init() {
		TaskConfig taskConfig = new TaskConfig();
		taskConfig.setMaxSleepTime(60 * 60 * 1000);
		config(taskConfig);
	}

}
