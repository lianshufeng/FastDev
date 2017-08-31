package com.fast.dev.component.taskpool.pool.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fast.dev.component.mongo.dao.domain.SuperEntity;
import com.fast.dev.component.taskpool.pool.TaskParameter;

@Document
public class TaskWork extends SuperEntity {
	// 任务实体
	private TaskParameter parameter;
	// 工作签名时间
	@Indexed
	private long lastSignInTime;

	public TaskParameter getParameter() {
		return parameter;
	}

	public void setParameter(TaskParameter parameter) {
		this.parameter = parameter;
	}

	public long getLastSignInTime() {
		return lastSignInTime;
	}

	public void setLastSignInTime(long lastSignInTime) {
		this.lastSignInTime = lastSignInTime;
	}

	public TaskWork() {
		// TODO Auto-generated constructor stub
	}

	public TaskWork(String taskId) {
		setId(taskId);
	}

}
