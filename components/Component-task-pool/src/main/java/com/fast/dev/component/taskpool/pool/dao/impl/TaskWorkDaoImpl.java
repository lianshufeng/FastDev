package com.fast.dev.component.taskpool.pool.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.fast.dev.component.mongo.util.EntityObjectUtil;
import com.fast.dev.component.taskpool.pool.TaskParameter;
import com.fast.dev.component.taskpool.pool.dao.TaskWorkDao;
import com.fast.dev.component.taskpool.pool.domain.TaskWork;
import com.mongodb.WriteResult;

public class TaskWorkDaoImpl implements TaskWorkDao {

	private MongoTemplate mongoTemplate;
	private String collectionName;

	public TaskWorkDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public TaskWorkDaoImpl(MongoTemplate mongoTemplate, String collectionName) {
		super();
		this.mongoTemplate = mongoTemplate;
		this.collectionName = collectionName;
	}

	@Override
	public String addTask(TaskParameter taskParameter) {
		TaskWork taskWork = new TaskWork();
		taskWork.setLastSignInTime(System.currentTimeMillis());
		taskWork.setParameter(taskParameter);
		EntityObjectUtil.preInsert(taskWork);
		this.mongoTemplate.save(taskWork, collectionName);
		return taskWork.getId();
	}

	@Override
	public void removeTask(String taskId) {
		this.mongoTemplate.remove(new TaskWork(taskId), collectionName);
	}

	@Override
	public boolean signInTask(String... taskId) {
		// 查询条件
		Query query = new Query();
		query.addCriteria(EntityObjectUtil.createQueryBatch("_id", taskId));
		// 任务签入时间
		if (taskId.length > 0) {
			Update update = new Update();
			update.set("lastSignInTime", System.currentTimeMillis());
			WriteResult wr = this.mongoTemplate.updateMulti(query, update, collectionName);
			return wr.getN() > 0;
		}
		return false;
	}

	@Override
	public List<TaskWork> loadTask(long timeOut, int maxCount) {
		List<TaskWork> taskWorks = new ArrayList<TaskWork>();
		for (int i = 0; i < maxCount; i++) {
			// 查询条件
			Query query = new Query();
			query.addCriteria(Criteria.where("lastSignInTime").lt(System.currentTimeMillis() - timeOut));
			// 更新内容
			Update update = new Update();
			update.set("lastSignInTime", System.currentTimeMillis());
			// 更新并签出任务
			TaskWork taskWork = this.mongoTemplate.findAndModify(query, update, TaskWork.class, collectionName);
			if (taskWork == null) {
				break;
			} else {
				taskWorks.add(taskWork);
			}
		}
		return taskWorks;
	}

}
