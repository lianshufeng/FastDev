package com.fast.dev.component.taskpool.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.fast.dev.component.taskpool.pool.TaskExecutor;
import com.fast.dev.component.taskpool.pool.TaskParameter;

/**
 * 任务池的配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年8月30日
 *
 */
public class TaskPoolConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 数据库操作对象 (必填)
	private MongoTemplate mongoTemplate = null;
	// 任务被调用的监听 (必填)
	private TaskExecutor<TaskParameter> taskExecutor = null;
	// 数据库中的表名，
	private String collectionName = "_taskWork";
	// 最大并发任务数量
	private int maxRunCount = 8;
	// 核心调度器周期,默认一分钟
	private long coreTime = 1 * 60 * 1000l;
	// 任务超时时间，必须大于核心调度器周期
	private long taskWorkTimeOut = 10 * 60 * 1000l;
	// 载入超时（失败）任务最大的数量
	private int maxLoadTaskCount = 10;
	// 最小开始加载超时（失败）任务的数量
	private int minBeginLoadCount = 8;

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public int getMaxLoadTaskCount() {
		return maxLoadTaskCount;
	}

	public void setMaxLoadTaskCount(int maxLoadTaskCount) {
		this.maxLoadTaskCount = maxLoadTaskCount;
	}

	public int getMinBeginLoadCount() {
		return minBeginLoadCount;
	}

	public void setMinBeginLoadCount(int minBeginLoadCount) {
		this.minBeginLoadCount = minBeginLoadCount;
	}

	public long getCoreTime() {
		return coreTime;
	}

	public void setCoreTime(long coreTime) {
		this.coreTime = coreTime;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * 数据库序列化操作对象
	 * 
	 * @param mongoTemplate
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public TaskExecutor<TaskParameter> getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor<TaskParameter> taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public int getMaxRunCount() {
		return maxRunCount;
	}

	public void setMaxRunCount(int maxRunCount) {
		this.maxRunCount = maxRunCount;
	}

	public long getTaskWorkTimeOut() {
		return taskWorkTimeOut;
	}

	public void setTaskWorkTimeOut(long taskWorkTimeOut) {
		this.taskWorkTimeOut = taskWorkTimeOut;
	}
}
