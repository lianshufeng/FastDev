package com.fast.dev.crawler.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.dao.impl.MongoDaoImpl;
import com.fast.dev.crawler.domain.TaskRecord;
import com.mongodb.WriteResult;

@Component
public class TaskRecordDao extends MongoDaoImpl<TaskRecord> {

	/**
	 * exist
	 * 
	 * @param taskName
	 * @return
	 */
	@Override
	public boolean exists(String taskName) {
		Update update = new Update();
		update.set("taskName", taskName);
		update.set("updateTime", dateUpdateHelper.getDbTime());
		update.inc("runCount", 1);
		update.setOnInsert("createTime", dateUpdateHelper.getDbTime());
		WriteResult writeResult = this.mongoTemplate
				.upsert(new Query().addCriteria(Criteria.where("taskName").is(taskName)), update, entityClass);
		return writeResult.isUpdateOfExisting();
	}

}
