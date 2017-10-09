package com.demo.mongodb.dao;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.demo.db.base.bean.User;
import com.demo.db.base.dao.UserDao;
import com.demo.mongodb.domain.UserTable;
import com.mongodb.WriteResult;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(User user) {
		UserTable userTable = new UserTable();
		BeanUtils.copyProperties(user, userTable);
		this.mongoTemplate.save(userTable);
		BeanUtils.copyProperties(userTable, user);
	}

	@Override
	public User[] get(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		List<UserTable> userTables = this.mongoTemplate.find(query, UserTable.class);
		User[] users = new User[userTables.size()];
		for (int i = 0; i < userTables.size(); i++) {
			users[i] = new User();
			BeanUtils.copyProperties(userTables.get(i), users[i]);
		}
		return users;
	}

	@Override
	public int count() {
		return Integer.parseInt(String.valueOf(this.mongoTemplate.count(new Query(), UserTable.class)));
	}

	@Override
	public int remove(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		WriteResult writeResult = this.mongoTemplate.remove(query, UserTable.class);
		return writeResult.getN();
	}

}
