package com.demo.db.base.dao;

import com.demo.db.base.bean.User;

public interface UserDao {

	/**
	 * 保存
	 * 
	 * @param name
	 */
	public void save(User user);

	/**
	 * 获取
	 * 
	 * @param name
	 * @return
	 */
	public User[] get(String name);

	/**
	 * 总数量
	 * 
	 * @return
	 */
	public int count();

	/**
	 * 删除
	 * 
	 * @param name
	 * @return
	 */
	public int remove(String name);

}
