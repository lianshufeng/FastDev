package com.fast.dev.crawler.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.dao.impl.MongoDaoImpl;
import com.fast.dev.crawler.domain.Resources;

@Component
public class ResourcesDao extends MongoDaoImpl<Resources> {

	/**
	 * 更新资源
	 * 
	 * @param title
	 * @param url
	 * @param createTime
	 */
	public void update(String title, String url, long publishTime) {
		Update update = new Update();
		update.set("url", url);
		update.set("title", title);
		update.set("publishTime", publishTime);
		
		update.set("updateTime", dateUpdateHelper.getDbTime());
		update.setOnInsert("createTime", dateUpdateHelper.getDbTime());
		
		this.mongoTemplate.upsert(new Query().addCriteria(Criteria.where("url").is(url)),update , entityClass);
	}

}
