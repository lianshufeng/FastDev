package com.fast.dev.core.task.impl.single;

import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fast.dev.core.helper.NewInstanceHelper;
import com.fast.dev.core.task.TaskManager;
import com.fast.dev.core.task.impl.single.model.TaskConfig;
import com.fast.dev.core.task.model.Task;

/**
 * 单任务管理器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月12日
 *
 */
@SuppressWarnings("unchecked")
public class SingleTaskManager extends NewInstanceHelper implements TaskManager {

	// 任务配置
	private TaskConfig taskConfig = new TaskConfig();

	// 定时器
	private Timer timer = new Timer();

	// 定时器任务缓存
	private final Map<Class<?>, SleepTask<?>> timerTaskCache = new ConcurrentHashMap<Class<?>, SleepTask<?>>();

	// 线程池
	ExecutorService threadPool = null;

	/**
	 * 执行线程任务
	 */
	protected <T> void executeThread(final Class<? extends Task<T>> taskClass, final T data) {
		if (this.threadPool == null) {
			// 实例化线程池
			this.threadPool = Executors.newFixedThreadPool(this.taskConfig.getMaxThreadCount());
		}
		// 通过线程池执行任务
		this.threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Task<T> task = (Task<T>) newInstance(taskClass);
					task.run(data);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					timerTaskCache.remove(taskClass);
				}
			}
		});

	}

	@Override
	public void shutdown() {
		// 关闭定时器
		if (timer != null) {
			timer.cancel();
		}
		// 关闭线程池
		if (this.threadPool != null) {
			this.threadPool.shutdownNow();
		}
	}

	/**
	 * 配置任务
	 */
	public void config(TaskConfig taskConfig) {
		this.taskConfig = taskConfig;
	}

	@Override
	public synchronized <T> boolean execute(Class<? extends Task<T>> taskClass, T data) {
		if (taskClass == null) {
			return false;
		}
		// 获取休眠任务
		SleepTask<T> sleepTask = (SleepTask<T>) this.timerTaskCache.get(taskClass);
		if (sleepTask == null) {
			sleepTask = new SleepTask<T>();
			// 追加到线程安全缓存里
			this.timerTaskCache.put(taskClass, sleepTask);
			// 设置执行的任务
			sleepTask.setTaskClass(taskClass);
			sleepTask.setSingleTaskManager(this);
			// 添加定时器任务
			this.timer.schedule(sleepTask, taskConfig.getMaxSleepTime());
		}
		sleepTask.setData(data);
		return true;
	}

}
