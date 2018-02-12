package com.fast.dev.data.transfer.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 标准的数据格式
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public class DataItem extends ArrayList<Object> implements Collection<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 实例化
	 */
	public DataItem() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造方法
	 * 
	 * @param data
	 */
	public DataItem(Collection<Object> datas) {
		this.addAll(datas);
	}

	/**
	 * 构造方法
	 * 
	 * @param datas
	 *            数组
	 */
	public DataItem(Object[] datas) {
		this.addAll(Arrays.asList(datas));
	}

}
