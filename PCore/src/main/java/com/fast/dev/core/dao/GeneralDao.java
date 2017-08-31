package com.fast.dev.core.dao;

import java.util.List;

import com.fast.dev.core.dao.model.Orders;

/**
 * 通用接口
 * 
 * @作者 练书锋
 * @时间 2016年5月13日
 */
public interface GeneralDao<T> {

	/**
	 * 获取当前的实体
	 * 
	 * @return
	 */
	public Class<T> getEntityClass();

	/**
	 * 保存或更新
	 * 
	 * @param t
	 */
	public void save(T t);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return 返回成功删除的记录
	 */
	public int remove(String... ids);

	/**
	 * 取出实体
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id);

	/**
	 * 取当前实体的所有记录
	 */
	public long count();

	/**
	 * 是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean exists(String id);

	/**
	 * 
	 * @param start
	 *            起始记录数
	 * @param limit
	 *            限制数
	 * @param descSort
	 *            倒叙排序
	 * @return
	 */
	public List<T> list(int skip, int limit, Orders... descSort);

	/**
	 * 设置时间戳
	 * 
	 * @param id
	 */
	public boolean updateTime(String id);

	/**
	 * 删除当前的表
	 */
	public void drop();

	/**
	 * 更新dbref的时间戳
	 * 
	 * @param t
	 */
	public void updateDBrefTime(T t);

}
