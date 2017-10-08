package com.fast.dev.hibernate.bean;

import java.util.Properties;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * 数据源的配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月8日
 *
 */
public class HibernateConfig {

	// hibernate的配置项目
	private Properties hibernate;

	// 数据源配置
	private BoneCPDataSource dataSource;

	// 设置hibernate要扫描的包名
	private String[] packagesToScan;

	public Properties getHibernate() {
		return hibernate;
	}

	public void setHibernate(Properties hibernate) {
		this.hibernate = hibernate;
	}

	public BoneCPDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BoneCPDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String[] getPackagesToScan() {
		return packagesToScan;
	}

	public void setPackagesToScan(String[] packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

}
