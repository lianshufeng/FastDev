package com.fast.dev.core.cast.container;

import org.springframework.stereotype.Component;

import com.fast.dev.core.cast.model.TimeCastModel;

/**
 * 时间开销容器线程
 * 
 * @author 练书锋
 *
 */
@Component
public class TimeCastThreadContainer {

	private ThreadLocal<TimeCastModel> threadLocal = new ThreadLocal<TimeCastModel>();

	/**
	 * 取出线程对象
	 * 
	 * @return
	 */
	public TimeCastModel get() {
		return this.threadLocal.get();
	}

	/**
	 * 设置线程对象
	 * 
	 * @param timeCastModel
	 */
	public void set(TimeCastModel timeCastModel) {
		this.threadLocal.set(timeCastModel);
	}

	/**
	 * 移出线程对象
	 */
	public void remove() {
		this.threadLocal.remove();
	}
}
