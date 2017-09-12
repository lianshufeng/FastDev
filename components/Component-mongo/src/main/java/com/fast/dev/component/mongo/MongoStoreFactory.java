package com.fast.dev.component.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;

import com.fast.dev.component.mongo.model.MongoConfig;
import com.fast.dev.core.util.code.JsonUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * mongodb的客户端生成组件
 * 
 * @名称 MongoComponentFactory.java
 * @作者 练书锋
 * @时间 2017年8月30日
 *
 */

public class MongoStoreFactory {

	/**
	 * 创建表格文件输入
	 * 
	 * @param mongoDbFactory
	 * @return
	 */
	private static GridFsTemplate createGridFsTemplate(MongoDbFactory mongoDbFactory) {
		try {
			MongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory),
					new MongoMappingContext());
			GridFsTemplate gridFsTemplate = new GridFsTemplate(mongoDbFactory, converter);
			return gridFsTemplate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	/**
	 * 创建 MongoDbFactory
	 * 
	 * @return
	 */
	private static MongoDbFactory createMongoDbFactory(MongoConfig mongoConfig) {
		// 配置集群
		String[] hosts = mongoConfig.getHost();
		List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
		if (hosts != null) {
			for (int i = 0; i < hosts.length; i++) {
				String[] hp = hosts[i].split(":");
				ServerAddress serverAddress = null;
				if (hp == null) {
					serverAddress = new ServerAddress("127.0.0.1", 27017);
				} else if (hp.length == 1) {
					serverAddress = new ServerAddress(hp[0], 27017);
				} else if (hp.length > 1) {
					serverAddress = new ServerAddress(hp[0], Integer.parseInt(hp[1]));
				}

				if (serverAddress != null) {
					serverAddressList.add(serverAddress);
				}
			}
		}

		// 数据库
		String dbName = mongoConfig.getDbName().trim();

		// 超时时间
		MongoClientOptions mongoClientOptions = MongoClientOptions.builder().socketTimeout(mongoConfig.getTimeOut())
				.connectTimeout(mongoConfig.getTimeOut()).serverSelectionTimeout(mongoConfig.getTimeOut()).build();

		// 创建mognodb的客户端
		MongoClient mongoClient;
		if (!StringUtils.isEmpty(mongoConfig.getUserName())) {
			String userName = mongoConfig.getUserName().trim();
			String passWord = mongoConfig.getPassWord().trim();
			MongoCredential mongoCredential = MongoCredential.createCredential(userName, dbName,
					passWord.toCharArray());
			mongoClient = new MongoClient(serverAddressList, Arrays.asList(mongoCredential), mongoClientOptions);
		} else {
			mongoClient = new MongoClient(serverAddressList, mongoClientOptions);
		}

		// mongodb工厂
		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, dbName);
		return mongoDbFactory;
	}

	/**
	 * 创建mongo对象
	 * 
	 * @param mongoConfig
	 * @return
	 */
	public static MongoStore create(MongoConfig mongoConfig) {
		//
		MongoDbFactory mongoDbFactory = createMongoDbFactory(mongoConfig);
		GridFsTemplate gridFsTemplate = createGridFsTemplate(mongoDbFactory);
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
		// 返回对象
		return new MongoStore(mongoTemplate, mongoDbFactory, gridFsTemplate);
	}

	/**
	 * 通过配置文件构建mongo对象
	 * 
	 * @param config
	 * @return
	 */
	public static MongoStore create(String configName) {
		MongoConfig componentConfig = null;
		try {
			componentConfig = JsonUtil.loadToObject(configName, MongoConfig.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return create(componentConfig);
	}

}
