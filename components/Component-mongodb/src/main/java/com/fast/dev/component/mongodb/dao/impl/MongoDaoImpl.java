package com.fast.dev.component.mongodb.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.fast.dev.component.mongodb.util.EntityObjectUtil;
import com.fast.dev.core.dao.GeneralDao;
import com.fast.dev.core.dao.model.Orders;
import com.mongodb.WriteResult;

/**
 * 通用的一些方法实现
 * 
 * @作者 练书锋
 * @时间 2016年5月13日
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class MongoDaoImpl<T> implements GeneralDao<T> {

	@Autowired
	protected MongoTemplate mongoTemplate;

	// 取出当前的实体类型
	protected Class<T> entityClass = getCls();

	private Class<T> getCls() {
		Type t = this.getClass().getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			return (Class<T>) p[0];
		}
		return null;
	}

	@Override
	public void save(T t) {
		this.mongoTemplate.save(t);
		updateDBrefTime(t);
	}

	@Override
	public int remove(String... ids) {
		if (ids == null || ids.length < 1) {
			return 0;
		}
		for (String id : ids) {
			updateDBrefTime(get(id));
		}
		Criteria criteria = createMultiIdsCriteria(ids);
		Query query = new Query();
		query.addCriteria(criteria);
		WriteResult writeResult = this.mongoTemplate.remove(query, this.entityClass);
		return writeResult.getN();
	}

	@Override
	public T get(String id) {
		return (T) this.mongoTemplate.findById(id, this.entityClass);
	}

	@Override
	public long count() {
		return this.mongoTemplate.count(new Query(), this.entityClass);
	}

	@Override
	public Class<T> getEntityClass() {
		return this.entityClass;
	}

	@Override
	public List<T> list(int skip, int limit, Orders... descSort) {
		Query query = new Query();
		query.skip(skip);
		query.limit(limit);
		return this.list(query, descSort);
	}

	/**
	 * 条件排序查询
	 * 
	 * @param query
	 * @param descSort
	 * @return
	 */
	public List<T> list(Query query, Orders... descSort) {
		if (descSort != null && descSort.length > 0) {
			List<Order> orders = new ArrayList<Order>();
			for (Orders sort : descSort) {
				orders.add(new Order(sort.isDesc() ? Direction.DESC : Direction.ASC, sort.getName()));
			}
			query.with(new Sort(orders));
		}
		return this.mongoTemplate.find(query, this.entityClass);
	}

	@Override
	public boolean updateTime(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		EntityObjectUtil.preUpdate(update);
		WriteResult writeResult = this.mongoTemplate.updateFirst(query, update, entityClass);
		return writeResult.getN() > 0;
	}

	@Override
	public void drop() {
		this.mongoTemplate.dropCollection(entityClass);
	}

	@Override
	public boolean exists(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return this.mongoTemplate.exists(query, entityClass);
	}

	/**
	 * 创建同时操作多个id的查询条件
	 * 
	 * @param ids
	 * @return
	 */
	protected static Criteria createMultiIdsCriteria(String... ids) {
		return EntityObjectUtil.createQueryBatch("_id", ids);
	}

	@Override
	public void updateDBrefTime(T t) {

	}
}
