package com.fast.dev.component.taskpool.pool;

/**
 * 任务执行器
 * 
 * @作者 练书锋
 * @时间 2016年7月16日
 *
 */
public interface TaskExecutor<T extends TaskParameter> {

	/**
	 * 任务被调用的时会触发本方法
	 * 
	 * @param task
	 */
	public void execute(T task);

}
