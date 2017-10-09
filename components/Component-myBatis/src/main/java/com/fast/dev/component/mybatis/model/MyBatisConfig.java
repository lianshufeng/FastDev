package com.fast.dev.component.mybatis.model;

import java.io.Serializable;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * 数据源配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月8日
 *
 */
public class MyBatisConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 数据源配置
	private BoneCPDataSource dataSource;

	// 扫描的包名，Dao接口的路径
	private String basePackage;

	// 配置文件路径 , 如:classpath:com/demo/mybatis/xml/**/*.xml, 如果不采用则为空
	// 推荐使用纯注解
	private String mapperLocations;

	public BoneCPDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BoneCPDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getMapperLocations() {
		return mapperLocations;
	}

	public void setMapperLocations(String mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

}
