package com.demo.mybatis.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.mybatis.spring.annotation.MapperScan;

import com.demo.db.base.bean.User;
import com.demo.db.base.dao.UserDao;

@MapperScan
public interface UserDaoImpl extends UserDao {

	@Select("select * from T_User where name = #{name} ")
	public User[] get(String name);

	@Select("select count(*) from T_User")
	public int count();

	@Insert("insert into T_User (id,name,time) values (  #{user.id} , #{user.name},#{user.time} )")
	@SelectKey(statement = "select replace(UUID(),'-','') as id", keyProperty = "user.id", before = true, statementType = StatementType.STATEMENT, resultType = String.class)
	public void save(@Param("user") User user);

	@Delete("delete from T_User where name = #{name} ")
	public int remove(String name);

}