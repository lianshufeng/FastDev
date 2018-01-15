package com.fast.dev.component.mongodb.helper.time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.core.task.model.Task;

/**
 * 请求时间任务
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月13日
 *
 */
@Component
public class RequestTimeTask extends Task<Void> {

	@Autowired
	private DBTimerHelper dbTimerHelper;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void run(Void data) {
		this.dbTimerHelper.updateDbTimeOffSetTime();
	}
}