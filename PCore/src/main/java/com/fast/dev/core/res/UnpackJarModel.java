package com.fast.dev.core.res;

/**
 * jar解压类型
 * 
 * @author 练书锋
 *
 */
public abstract class UnpackJarModel {

	/**
	 * 获取模块名称
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 原路径
	 * 
	 * @return
	 */
	public abstract String getSource();

	/**
	 * 目标路径
	 * 
	 * @return
	 */
	public abstract String getTarget();

	/**
	 * 资源处理方式
	 * 
	 * @return
	 */
	public abstract UnpackType getUnpackType();
}
