package com.fast.dev.component.taskpool.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fast.dev.component.taskpool.pool.dao.TaskWorkDao;
import com.fast.dev.component.taskpool.pool.dao.impl.TaskWorkDaoImpl;
import com.fast.dev.component.taskpool.pool.domain.TaskWork;

public class TaskManagerImpl extends TaskManager {

	// 数据库工具
	private TaskWorkDao taskWorkDao;
	// 任务池
	private ExecutorService taskExecutorService;
	private ExecutorService coreTimerExecutorService;
	// 核心任务时钟
	private Timer coreTimer = new Timer();
	// 工作内容，必须是线程安全的map
	private final Map<String, TaskParameter> tasks = new ConcurrentHashMap<String, TaskParameter>();
	// 记录是否完成初始化任务
	private boolean finishInit = false;

	@Override
	public synchronized void executeTask(final TaskParameter taskParameter) {
		// 任务入库
		final String taskId = regTask(taskParameter);
		// 开始执行任务
		insertExecuteTask(taskId, taskParameter);
	}

	public void init() {
		if (!finishInit) {
			this.taskWorkDao = new TaskWorkDaoImpl(this.taskPoolConfig.getMongoTemplate(),
					this.taskPoolConfig.getCollectionName());
			this.taskExecutorService = Executors.newFixedThreadPool(this.taskPoolConfig.getMaxRunCount());
			this.coreTimerExecutorService = Executors.newFixedThreadPool(1);
			// 启动核心时钟
			coreTimer();
		}
		finishInit = true;
	}

	@Override
	public void shutdown() {
		this.coreTimer.cancel();
		this.taskExecutorService.shutdownNow();
		this.coreTimerExecutorService.shutdownNow();
	}

	/**
	 * 执行任务
	 * 
	 * @param taskParameter
	 */
	private void execute(final String taskId, final TaskParameter taskParameter) {
		// 任务执行之前
		preWork(taskId, taskParameter);
		// 启动线程
		this.taskExecutorService.execute(new Runnable() {
			@Override
			public void run() {
				// 执行任务
				working(taskParameter);
				// 任务执行后
				afterWork(taskId);
			}
		});

	}

	/**
	 * 开始工作
	 * 
	 * @param task
	 */
	private void working(final TaskParameter task) {
		try {
			this.taskPoolConfig.getTaskExecutor().execute(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在任务工作之前
	 * 
	 * @param taskParameter
	 */
	private void preWork(String taskId, final TaskParameter taskParameter) {
		// 记录到内存里
		tasks.put(taskId, taskParameter);
		// 通过调度器，统一签入执行时间
		// 数据库签名该任务已被执行
		// taskWorkDao.signInTask(taskId);
	}

	/**
	 * 任务工作之后
	 * 
	 * @param taskId
	 */
	private void afterWork(final String taskId) {
		// 记录到内存里
		tasks.remove(taskId);
		// 完成任务
		finishTask(taskId);
	}

	/**
	 * 登记任务
	 * 
	 * @param taskParameter
	 * @return 返回任务id
	 */
	private String regTask(final TaskParameter taskParameter) {
		return this.taskWorkDao.addTask(taskParameter);
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 */
	private void finishTask(final String taskId) {
		this.taskWorkDao.removeTask(taskId);
	}

	/**
	 * 核心调度器
	 */
	private void coreTimer() {
		this.coreTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				coreTimerExecutorService.execute(new Runnable() {
					@Override
					public void run() {
						// 触发事件
						coreTaskEvent();
						// 核心调度完成当前任务，则继续延迟调用本事件
						coreTimer();
					}
				});
			}
		}, this.taskPoolConfig.getCoreTime());
	}

	/**
	 * 核心任务调度器
	 */
	private void coreTaskEvent() {
		// 记录超时任务
		checkInTask();
		// 自动载入超时
		loadTimeOutTask();
	}

	// 数据库里签入任务工作时间戳，防止任务超时被其他集群点读取该任务
	private void checkInTask() {
		List<String> ids = new ArrayList<String>();
		// 遍历任务,记录所有队列中的id
		for (Entry<String, TaskParameter> entry : tasks.entrySet()) {
			ids.add(entry.getKey());
		}
		if (ids.size() > 0) {
			// 数据库嵌入这些任务的工作时间，防止任务超时被其他集群占用
			this.taskWorkDao.signInTask(ids.toArray(new String[ids.size()]));
		}
	}

	/**
	 * 载入超时(失败）任务
	 */
	private void loadTimeOutTask() {
		if (isAddExecuteTask()) {
			List<TaskWork> taskWorks = this.taskWorkDao.loadTask(this.taskPoolConfig.getTaskWorkTimeOut(),
					this.taskPoolConfig.getMaxLoadTaskCount());
			if (taskWorks != null) {
				for (TaskWork taskWork : taskWorks) {
					execute(taskWork.getId(), taskWork.getParameter());
				}
			}
		}
	}

	/**
	 * 判断是否可以添加任务执行
	 * 
	 * @return
	 */
	private boolean isAddExecuteTask() {
		return tasks.size() <= this.taskPoolConfig.getMinBeginLoadCount();
	}

	/**
	 * 条件加入当前可执行的任务队列里
	 * 
	 * @param taskId
	 * @param taskParameter
	 */
	private void insertExecuteTask(final String taskId, final TaskParameter taskParameter) {
		if (isAddExecuteTask()) {
			execute(taskId, taskParameter);
		}
	}

}
