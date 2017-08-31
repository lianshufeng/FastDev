package com.fast.dev.component.mongo;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * mongo的生成客户端
 * 
 * @名称 MongoStore.java
 * @作者 练书锋
 * @时间 2017年8月30日
 *
 */
public class MongoStore {

	//
	private MongoTemplate mongoTemplate;

	private MongoDbFactory mongoDbFactory;

	private GridFsTemplate gridFsTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public MongoDbFactory getMongoDbFactory() {
		return mongoDbFactory;
	}

	protected void setMongoDbFactory(MongoDbFactory mongoDbFactory) {
		this.mongoDbFactory = mongoDbFactory;
	}

	public GridFsTemplate getGridFsTemplate() {
		return gridFsTemplate;
	}

	protected void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
		this.gridFsTemplate = gridFsTemplate;
	}

	protected MongoStore(MongoTemplate mongoTemplate, MongoDbFactory mongoDbFactory, GridFsTemplate gridFsTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;
		this.mongoDbFactory = mongoDbFactory;
		this.gridFsTemplate = gridFsTemplate;
	}

	public MongoStore() {
		// TODO Auto-generated constructor stub
	}

}
