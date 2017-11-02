package com.fast.dev.example.security.model;

import java.io.Serializable;

/**
 * 用户基本信息
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月1日
 *
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 姓名
	private String name;

	// 年龄
	private int age;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public UserInfo(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	
	public UserInfo() {
		// TODO Auto-generated constructor stub
	}

}
