package com.demo.redis.dao;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.demo.db.base.bean.User;
import com.demo.db.base.dao.UserDao;
import com.demo.redis.domain.UserTable;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private RedisTemplate<String, UserTable> redisTemplate;

	@Override
	public void save(User user) {
		// 设置主键
		user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		UserTable userTable = new UserTable();
		BeanUtils.copyProperties(user, userTable);
		SetOperations<String, UserTable> setOperations = opsForSet();
		setOperations.add(userTable.getName(), userTable);
	}

	@Override
	public User[] get(String name) {
		SetOperations<String, UserTable> setOperations = opsForSet();
		Set<UserTable> userTables = setOperations.members(name);
		UserTable[] users = new UserTable[userTables.size()];
		userTables.toArray(users);
		return users;
	}

	@Override
	public int count() {
		SetOperations<String, UserTable> setOperations = opsForSet();
		Set<String> keys = this.redisTemplate.keys("*");
		int count = 0;
		for (String key : keys) {
			count += setOperations.size(key);
		}
		return count;
	}

	@Override
	public int remove(String name) {
		long count = opsForSet().size(name);
		this.redisTemplate.delete(name);
		return Integer.parseInt(String.valueOf(count));
	}

	/**
	 * 获取set
	 * 
	 * @return
	 */
	private SetOperations<String, UserTable> opsForSet() {
		return this.redisTemplate.opsForSet();
	}

}
