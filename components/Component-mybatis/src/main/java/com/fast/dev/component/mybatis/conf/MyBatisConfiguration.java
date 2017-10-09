package com.fast.dev.component.mybatis.conf;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fast.dev.component.mybatis.model.MyBatisConfig;

/**
 * MyBatis配置，托管给spring事务
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月8日
 *
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfiguration {

	private final static String SQLSESSIONFACTORYNAME = "sqlSessionFactory";

	/**
	 * 配置事务
	 * 
	 * @return
	 * @throws IOException
	 */
	@Bean
	public PlatformTransactionManager transactionManager(MyBatisConfig myBatisConfig) throws Exception {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource(myBatisConfig));
		return transactionManager;
	}

	/**
	 * SQLl的session工厂
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean(SQLSESSIONFACTORYNAME)
	public SqlSessionFactory sqlSessionFactory(MyBatisConfig myBatisConfig) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		// 数据源
		sqlSessionFactoryBean.setDataSource(dataSource(myBatisConfig));

		// xml资源
		String mapperLocations = myBatisConfig.getMapperLocations();
		if (mapperLocations != null) {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
		}
		// 获取SqlSessionFactory
		SqlSessionFactory sessionFactory = sqlSessionFactoryBean.getObject();
		return sessionFactory;
	}

	/**
	 * 配置数据源
	 * 
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Bean
	public DataSource dataSource(MyBatisConfig myBatisConfig) throws Exception {
		return myBatisConfig.getDataSource();
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(MyBatisConfig myBatisConfig) {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		// 设置扫描的包
		mapperScannerConfigurer.setBasePackage(myBatisConfig.getBasePackage());
		// 设置sessionBean名称
		mapperScannerConfigurer.setSqlSessionFactoryBeanName(SQLSESSIONFACTORYNAME);
		return mapperScannerConfigurer;
	}

}
