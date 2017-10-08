package com.demo.hibernate.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.hibernate.dao.UserDao;
import com.demo.hibernate.domain.UserTable;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String save(String name) {
		UserTable userTable = new UserTable();
		userTable.setName(name);
		userTable.setTime(System.currentTimeMillis());
		getSession().save(userTable);
		return userTable.getId();
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
}
