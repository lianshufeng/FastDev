package com.fast.dev.component.redis.model;

import java.io.Serializable;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis的配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月9日
 *
 */
public class RedisConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 连接池配置
	private JedisPoolConfig pool;

	// 数据源
	private JedisConnectionFactory dataSource;

	public JedisPoolConfig getPool() {
		return pool;
	}

	public void setPool(JedisPoolConfig pool) {
		this.pool = pool;
	}

	public JedisConnectionFactory getDataSource() {
		return dataSource;
	}

	public void setDataSource(JedisConnectionFactory dataSource) {
		this.dataSource = dataSource;
	}

}
