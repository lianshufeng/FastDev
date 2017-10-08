package com.demo.mybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import com.demo.mybatis.bean.User;

@MapperScan
public interface UserDao {

	@Select("select * from User where name = #{name} ")
	public User[] getUser(String name);

	@Insert("insert into User (id,name) values (  #{id} , #{name} )")
	public void save(User user);

	@Select("select count(*) from User")
	public int count();

}