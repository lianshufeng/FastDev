package com.demo.mybatis.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mybatis.bean.User;
import com.demo.mybatis.dao.UserDao;

@Service
@Transactional(readOnly = false)
public class UserService {

	@Resource
	private UserDao userDao;

	/**
	 * 取一个用户
	 * @param id
	 * @return
	 */
	public User[] getUser(String name) {
		return this.userDao.getUser(name);
	}

	
	/**
	 * 获取总量
	 * @return
	 */
	public int count() {
		return this.userDao.count();
	}
	
	public User save(String name) {
		User user = new User();
		user.setName(name);
		user.setId(UUID.randomUUID().toString());
		userDao.save(user);
		return user;
	}

}