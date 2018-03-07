package com.fast.dev.crawler.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.helper.time.DBTimerHelper;
import com.fast.dev.crawler.domain.TimerListTask;

@Component
public class CrawlerTaskDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private DBTimerHelper dbTimerHelper;;

	// public void update(String taskName) {
	// Query query = new
	// Query().addCriteria(Criteria.where("taskName").is(taskName));
	// Update update = new Update();
	// update.setOnInsert("createTime", dbTimerHelper.getDBTime());
	// update.set("updateTime", dbTimerHelper.getDBTime());
	// this.mongoTemplate.updateFirst(query, update, CallListTask.class);
	// }

	/**
	 * 添加爬虫定时器任务状态
	 * 
	 * @param taskName
	 * @param endInfo
	 * @param nextPagge
	 */
	public void updateTimerTask(String taskName, String endInfo, String nextPage) {
		Query query = new Query().addCriteria(Criteria.where("taskName").is(taskName));
		Update update = new Update();
		update.set("endInfo", endInfo);
		update.set("nextPage", nextPage);
		update.set("updateTime", dbTimerHelper.getDBTime());
		update.setOnInsert("createTime", dbTimerHelper.getDBTime());
		this.mongoTemplate.upsert(query, update, TimerListTask.class);
	}

	/**
	 * 获取定时器任务
	 * 
	 * @return
	 */
	public synchronized TimerListTask findAndRemoveTimerTask(String taskName) {
		Query query = new Query().addCriteria(Criteria.where("taskName").is(taskName));
		return this.mongoTemplate.findAndRemove(query, TimerListTask.class);
	}

}
