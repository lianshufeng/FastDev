package com.fast.dev.crawler.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

@Document
public class TimerListTask extends SuperEntity {

	@Indexed(unique = true)
	private String taskName;

	// 结束标识
	private String endInfo;

	// 下一页地址
	private String nextPage;

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the endInfo
	 */
	public String getEndInfo() {
		return endInfo;
	}

	/**
	 * @param endInfo
	 *            the endInfo to set
	 */
	public void setEndInfo(String endInfo) {
		this.endInfo = endInfo;
	}

	/**
	 * @return the nextPage
	 */
	public String getNextPage() {
		return nextPage;
	}

	/**
	 * @param nextPage
	 *            the nextPage to set
	 */
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

}
