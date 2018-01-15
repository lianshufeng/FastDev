package com.fast.dev.component.mongodb.helper.update;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;
import com.fast.dev.component.mongodb.helper.time.DBTimerHelper;

/**
 * 日期查询工具
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月3日
 *
 */
@Component
public class DataUpdateHelper {

	private final static String CreateTimeName = "createTime";
	private final static String UpdateTimeName = "updateTime";

	@Resource
	private MongoTemplate mongoTemplate;

	@Resource
	private DBTimerHelper dbTimerHelper;

	/**
	 * 查询时间模型
	 * 
	 * @param entityCls
	 *            (查询实体)
	 * @param queryTimeType
	 *            时间类型 (创建时间或者修改时间)
	 * @param directionType
	 *            时间方向 （前|后）
	 * @param timeValue
	 *            （时间值）
	 * @param count
	 *            （参考值）
	 * @param criterias
	 *            (扩展的查询条件)
	 * @return 返回查询时间模型
	 */
	public Result list(final Class<? extends SuperEntity> entityCls, final QueryTimeType queryTimeType,
			final QueryDirectionType directionType, final Long timeValue, final int count,
			final Criteria... criterias) {

		// 构建查询条件
		Query query = createQuery(queryTimeType, directionType, timeValue, criterias);

		// 查询限制
		query.limit(count);

		// 首次查询
		List<? extends SuperEntity> result = this.mongoTemplate.find(query, entityCls);
		if (result == null || result.size() == 0) {
			return new Result(result, 0);
		}

		// 取出相同的时间值相当于需要排开查询的值
		List<String> sameDataIdes = new ArrayList<String>();

		// 取出相同时间
		long lastEntityTime = getEntitySameIds(result, queryTimeType, sameDataIdes);

		// 查询与时间相同的数据
		List<? extends SuperEntity> sameTimeEntity = querySameTimeData(entityCls, lastEntityTime, queryTimeType,
				sameDataIdes);

		// 返回的数据实体
		List<SuperEntity> entitys = new ArrayList<>(result);
		// 合并两次查询结果
		if (sameTimeEntity != null && sameTimeEntity.size() > 0) {
			entitys.addAll(sameTimeEntity);
		}
		// 按照该查询条件是否还有数据
		long size = count(entityCls, queryTimeType, directionType, lastEntityTime, criterias);
		return new Result(entitys, size);

	}

	/**
	 * 查询结果
	 * 
	 * @param entityCls
	 * @param queryTimeType
	 * @param directionType
	 * @param timeValue
	 * @param criterias
	 * @return
	 */
	public long count(Class<? extends SuperEntity> entityCls, QueryTimeType queryTimeType,
			QueryDirectionType directionType, Long timeValue, Criteria... criterias) {
		Query query = createQuery(queryTimeType, directionType, timeValue, criterias);
		return this.mongoTemplate.count(query, entityCls);
	}

	/**
	 * 保存之前执行
	 * 
	 * @param superEntity
	 */
	public void preInsert(SuperEntity superEntity) {
		long dbTime = getDbTime();
		superEntity.setId(null);
		superEntity.setCreateTime(dbTime);
		superEntity.setUpdateTime(dbTime);
	}

	/**
	 * 更新之前执行
	 * 
	 * @param superEntity
	 * @return
	 */
	public void preUpdate(SuperEntity superEntity) {
		long dbTime = getDbTime();
		superEntity.setUpdateTime(dbTime);
	}

	/**
	 * 插入或更新之前
	 * 
	 * @param update
	 */
	public void preUpdate(Update update) {
		long dbTime = getDbTime();
		update.setOnInsert(CreateTimeName, dbTime);
		update.set(UpdateTimeName, dbTime);
	}

	/**
	 * 获取DB的时间
	 * 
	 * @return
	 */
	public long getDbTime() {
		return this.dbTimerHelper.getDBTime();
	}

	/**
	 * 创建查询
	 * 
	 * @param entityCls
	 * @param queryTimeType
	 * @param directionType
	 * @param timeValue
	 * @param count
	 * @param criterias
	 * @return
	 */
	private Query createQuery(QueryTimeType queryTimeType, QueryDirectionType directionType, Long timeValue,
			Criteria... criterias) {
		// 时间值
		long timeVal = timeValue == null ? getDbTime() : timeValue;

		// 构建查询条件
		Query query = new Query();
		// 时间字段
		String timeName = (queryTimeType == QueryTimeType.CreateTime ? CreateTimeName : UpdateTimeName);
		Direction direction = directionType == QueryDirectionType.After ? Direction.ASC : Direction.DESC;
		Sort sort = new Sort(new Sort.Order(direction, timeName));
		query.with(sort);

		// 过滤条件
		Criteria where = new Criteria(timeName);
		if (directionType == QueryDirectionType.Before) {
			where.lt(timeVal);
		} else {
			where.gt(timeVal);
		}
		// 增加条件
		query.addCriteria(where);

		// 自定义查询条件
		if (criterias != null && criterias.length > 0) {
			for (Criteria criteria : criterias) {
				query.addCriteria(criteria);
			}
		}
		return query;
	}

	/**
	 * 查询时间相同的数据
	 * 
	 * @param timeValue
	 * @param queryTimeType
	 * @param excludeIds
	 * @return
	 */
	private List<? extends SuperEntity> querySameTimeData(Class<? extends SuperEntity> entityCls, long timeValue,
			QueryTimeType queryTimeType, List<String> excludeIds) {
		List<? extends SuperEntity> result = new ArrayList<>();

		String timeName = null;
		if (queryTimeType == QueryTimeType.CreateTime) {
			timeName = CreateTimeName;
		} else {
			timeName = UpdateTimeName;
		}

		// 查询时间相同的数据
		Criteria where = Criteria.where(timeName).is(timeValue);

		if (excludeIds != null && excludeIds.size() > 0) {
			where.and("_id").nin(excludeIds);
		}

		Query query = new Query();
		query.addCriteria(where);
		this.mongoTemplate.find(query, entityCls);

		return result;
	}

	/**
	 * 在实体类查询相同的时间数据
	 * 
	 * @param list
	 * @param queryTimeType
	 * @return 返回这些数据的id
	 */
	private static long getEntitySameIds(final List<? extends SuperEntity> list, final QueryTimeType queryTimeType,
			final List<String> result) {

		// 取出最后一个实体
		SuperEntity lastEntity = list.get(list.size() - 1);
		// 取出相同的值
		long lastEntityTimeValue = getEntityTime(lastEntity, queryTimeType);
		for (SuperEntity entity : list) {
			long entityTimeValue = getEntityTime(entity, queryTimeType);
			if (entityTimeValue == lastEntityTimeValue) {
				result.add(entity.getId());
			}
		}
		return lastEntityTimeValue;
	}

	/**
	 * 取实体数据的时间
	 * 
	 * @param entity
	 * @param queryTimeType
	 * @return
	 */
	private static long getEntityTime(SuperEntity entity, QueryTimeType queryTimeType) {
		long timeValue = 0;
		if (queryTimeType == QueryTimeType.CreateTime) {
			timeValue = entity.getCreateTime();
		} else {
			timeValue = entity.getUpdateTime();
		}
		return timeValue;
	}

	/**
	 * 查询时间类型
	 * 
	 * @作者 练书锋
	 * @联系 251708339@qq.com
	 * @时间 2018年1月4日
	 *
	 */
	public enum QueryTimeType {
		CreateTime, UpdateTime
	}

	public enum QueryDirectionType {
		Before, After
	}

	public class Result {

		// 数据集
		private List<? extends SuperEntity> result;

		// 时候还有数据
		private long next;

		public Result(List<? extends SuperEntity> result, long next) {
			super();
			this.result = result;
			this.next = next;
		}

		/**
		 * @return the result
		 */
		public List<? extends SuperEntity> getResult() {
			return result;
		}

		/**
		 * @param result
		 *            the result to set
		 */
		public void setResult(List<? extends SuperEntity> result) {
			this.result = result;
		}

		/**
		 * @return the next
		 */
		public long getNext() {
			return next;
		}

		/**
		 * @param next
		 *            the next to set
		 */
		public void setNext(long next) {
			this.next = next;
		}

		public Result() {
			// TODO Auto-generated constructor stub
		}

	}

}
