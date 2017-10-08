package com.fast.dev.hibernate.conf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fast.dev.hibernate.bean.HibernateConfig;

/**
 * hibernate配置，托管给spring事务
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月8日
 *
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	@Autowired
	private HibernateConfig dataSourceConfig;

	/**
	 * 配置事务
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;
	}

	/**
	 * 配置session
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public SessionFactory sessionFactory() throws Exception {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		// 设置数据源
		localSessionFactoryBean.setDataSource(dataSource());
		// 设置hibernate配置项
		localSessionFactoryBean.setHibernateProperties(this.dataSourceConfig.getHibernate());
		// 设置扫描的包名
		localSessionFactoryBean.setPackagesToScan(this.dataSourceConfig.getPackagesToScan());
		// 初始化完成
		localSessionFactoryBean.afterPropertiesSet();
		return localSessionFactoryBean.getObject();
	}

	/**
	 * 配置数据源
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Bean
	public DataSource dataSource() throws Exception {
		return this.dataSourceConfig.getDataSource();
	}

}
