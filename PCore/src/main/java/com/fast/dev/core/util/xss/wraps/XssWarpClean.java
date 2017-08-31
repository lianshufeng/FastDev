package com.fast.dev.core.util.xss.wraps;

/**
 * 对象类型数据清洗器
 * 
 * @作者 练书锋
 * @时间 2017年6月9日
 *
 */
public interface XssWarpClean<T extends Object> {

	/**
	 * 封装包
	 * 
	 * @param o
	 */
	public Object warp(T o);

}
