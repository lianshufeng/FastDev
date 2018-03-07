package com.fast.dev.crawler.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

@Document
public class TaskRecord extends SuperEntity {

	@Indexed(unique = true)
	private String taskName;

	// 运行次数
	private long runCount;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getRunCount() {
		return runCount;
	}

	public void setRunCount(long runCount) {
		this.runCount = runCount;
	}

}
