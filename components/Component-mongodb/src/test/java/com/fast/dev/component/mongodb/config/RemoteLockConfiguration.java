package com.fast.dev.component.mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fast.dev.component.mongodb.lock.RemoteLockMongo;
import com.fast.dev.component.mongodb.lock.config.RemoteLockOption;
import com.fast.dev.component.mongodb.model.MongodbConfig;
import com.fast.dev.core.lock.factory.RemoteLock;
import com.fast.dev.core.lock.factory.RemoteLockFactory;

@Configuration
public class RemoteLockConfiguration {

	@Bean
	public MongodbConfig mongodbConfig() {
		// 需要注入的对象
		MongodbConfig mongodbConfig = new MongodbConfig();
		mongodbConfig.setDbName("test");
		mongodbConfig.setHost(new String[] { "127.0.0.1:27017" });
		mongodbConfig.setConnectionsPerHost(200);
		return mongodbConfig;
	}
	
	@Bean
	public RemoteLock remoteLock() throws Exception {
		RemoteLockOption remoteLockOption = new RemoteLockOption();
		remoteLockOption.setMaxLiveTime(5000);
		return RemoteLockFactory.build(RemoteLockMongo.class, remoteLockOption);
	}
	

}
