package com.demo.db.base.service;

import com.demo.db.base.bean.User;

public interface UserService {

	/**
	 * 保存
	 * 
	 * @param name
	 * @return
	 */
	public String save(String name);

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
