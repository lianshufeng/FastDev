package com.fast.dev.component.jdbcpool;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * 数据源工厂
 * 
 * @作者 练书锋
 * @时间 2015年10月9日
 *
 */
public class JdbcDataSourceFactory {

	/**
	 * 创建 JDBC的连接池
	 * 
	 * @param driverclass
	 * @param jdbcUrl
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public static DataSource create(String driverclass, String jdbcUrl, String userName, String passWord) {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(driverclass);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		// dataSource.setTransactionRecoveryEnabled(false);
		// dataSource.setDefaultAutoCommit(false);
		// 设置间隔检查时间
		// dataSource.setIdleConnectionTestPeriod(10, TimeUnit.SECONDS);
		// //设置连接最大存活时间
		// dataSource.setIdleMaxAge(2, TimeUnit.MINUTES);
		// 设置获取connection超时的时间(毫秒)
		// dataSource.setConnectionTimeout(6, TimeUnit.SECONDS);
		return dataSource;
	}

}
