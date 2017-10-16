package com.fast.dev.component.notice;

/**
 * 通知接口，请实现本接口并配置到spring的容器里
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2016年12月29日
 *
 * @param <T>
 */
public interface NoticeExecute<T> {

	/**
	 * 通知
	 * 
	 * @param t
	 * @return 如果返回 false 则不在继续冒泡通知下去
	 */
	public boolean execute(String subscribe, T t);

	/**
	 * 订阅
	 * 
	 * @return
	 */
	public String[] subscribes();

	/**
	 * 排序，递增
	 * 
	 * @return
	 */
	public int order();

}
