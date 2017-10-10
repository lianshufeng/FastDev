package com.fast.dev.component.mongodb.model;

import java.io.Serializable;

public class MongodbConfig implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户名，可空
	private String userName;
	// 密码，可空
	private String passWord;
	// IP+端口 , 中间用冒号间隔
	private String[] host;
	// 数据库名
	private String dbName;
	// 超时时间
	private int timeOut = 30000;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String[] getHost() {
		return host;
	}
	public void setHost(String[] host) {
		this.host = host;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	
	
	
}
