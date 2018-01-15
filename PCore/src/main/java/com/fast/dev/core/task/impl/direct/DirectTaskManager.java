package com.fast.dev.core.task.impl.direct;

import com.fast.dev.core.helper.NewInstanceHelper;
import com.fast.dev.core.task.TaskManager;
import com.fast.dev.core.task.model.Task;

/**
 * 同步直接直接执行任务
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
public class DirectTaskManager extends NewInstanceHelper implements TaskManager {

	@Override
	@Deprecated
	public void shutdown() {

	}

	@Override
	public <T> boolean execute(Class<? extends Task<T>> taskClass, T data) {
		newInstance(taskClass).run(data);
		return true;
	}

}
