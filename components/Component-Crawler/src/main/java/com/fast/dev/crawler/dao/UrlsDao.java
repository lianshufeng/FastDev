package com.fast.dev.crawler.dao;

import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.fast.dev.component.mongodb.dao.impl.MongoDaoImpl;

public abstract class UrlsDao<T> extends MongoDaoImpl<T> {

	/**
	 * 更新记录
	 * 
	 * @param url
	 */
	public void update(String taskName, String url, Map<String, Object> data) {
		Update update = new Update();
		update.set("url", url);
		update.set("taskName", taskName);
		update.set("data", data);
		update.set("updateTime", this.dateUpdateHelper.getDbTime());
		update.setOnInsert("createTime", this.dateUpdateHelper.getDbTime());
		this.mongoTemplate.upsert(new Query().addCriteria(Criteria.where("url").is(url).and("taskName").is(taskName)),
				update, entityClass);
	}

	/**
	 * 寻找该任务的一条记录,先插入的优先取出
	 * 
	 * @param taskName
	 * @return
	 */
	public T findAndRemove(String taskName) {
		Query query = new Query().addCriteria(Criteria.where("taskName").is(taskName));
		query.limit(1);
		query.with(new Sort(Direction.ASC, "createTime"));
		return this.mongoTemplate.findAndRemove(query, entityClass);
	}

}
