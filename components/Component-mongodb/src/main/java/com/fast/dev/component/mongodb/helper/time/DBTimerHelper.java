package com.fast.dev.component.mongodb.helper.time;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.conf.SingleTaskManagerExt;

@Component
public class DBTimerHelper {

	@Autowired
	private MongoTemplate mongoTemplate;

	// 单任务管理器
	@Autowired
	private SingleTaskManagerExt taskManager;

	// DB的时间偏移
	private Long dBOffSetTime = null;

	@PreDestroy
	private void shutdown() {
		if (this.taskManager != null) {
			this.taskManager.shutdown();
		}
	}

	/**
	 * 获取DB时间
	 * 
	 * @return
	 */
	public long getDBTime() {
		startRequestTimeTask();
		return getlocalTime() + dBOffSetTime;
	}

	/**
	 * 启动请求时间任务
	 * 
	 * @param isThread
	 */
	private void startRequestTimeTask() {
		if (dBOffSetTime == null) {
			updateDbTimeOffSetTime();
		} else {
			this.taskManager.execute(RequestTimeTask.class, null);
		}
	}

	/**
	 * 更新获取DB的时间偏移
	 */
	protected void updateDbTimeOffSetTime() {
		dBOffSetTime = this.requestDBTime() - System.currentTimeMillis();
	}

	/**
	 * 获取本地时间
	 */
	private long getlocalTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 请求数据库时间
	 * 
	 * @return
	 */
	protected long requestDBTime() {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(DBTimerEntity.idValues));
		Update update = new Update();
		update.currentDate("date");
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.upsert(true);
		options.returnNew(true);
		DBTimerEntity dbTimerEntity = this.mongoTemplate.findAndModify(query, update, options, DBTimerEntity.class);
		return dbTimerEntity.getDate().getTime();
	}

}
