package com.demo.db.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.db.base.bean.User;
import com.demo.db.base.dao.UserDao;
import com.demo.db.base.service.UserService;

@Service
@Transactional(readOnly = false, rollbackFor = { Exception.class })
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public String save(String name) {
		User user = new User();
		user.setName(name);
		user.setTime(System.currentTimeMillis());
		this.userDao.save(user);
		return user.getId();
	}

	@Override
	public User[] get(String name) {
		return this.userDao.get(name);
	}

	@Override
	public int count() {
		return this.userDao.count();
	}

	@Override
	public int remove(String name) {
		return this.userDao.remove(name);
	}

}
