package com.demo.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.db.base.bean.User;
import com.demo.db.base.dao.UserDao;
import com.demo.hibernate.domain.UserTable;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void save(User user) {
		UserTable userTable = new UserTable();
		BeanUtils.copyProperties(user, userTable);
		getSession().save(userTable);
		BeanUtils.copyProperties(userTable, user);
	}

	@Override
	public User[] get(String name) {
		Query<User> query = getSession().createQuery("FROM UserTable Where name = :name ", User.class);
		query.setParameter("name", name, StringType.INSTANCE);
		List<User> userTables = query.list();
		User[] users = new User[userTables.size()];
		userTables.toArray(users);
		return users;
	}

	@Override
	public int count() {
		Query<Object> query = getSession().createQuery("select count (*) from UserTable", Object.class);
		return Integer.valueOf(String.valueOf(query.list().get(0)));
	}

	@Override
	public int remove(String name) {
		User[] users = get(name);
		for (User user : users) {
			getSession().remove(user);
		}
		return users.length;
	}

	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
}
