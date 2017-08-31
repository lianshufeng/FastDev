package com.fast.dev.component.notice;

/**
 * 消息管理器
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2016年12月29日
 *
 */
public interface MessageManager {

	/**
	 * 发布消息到订阅
	 * 
	 * @param t
	 * @param subscribes
	 */
	public <T> void publish(T t, String... subscribes);

}
