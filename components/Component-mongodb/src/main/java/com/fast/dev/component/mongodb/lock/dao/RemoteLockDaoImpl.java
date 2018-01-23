package com.fast.dev.component.mongodb.lock.dao;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.component.mongodb.dao.impl.MongoDaoImpl;
import com.fast.dev.component.mongodb.lock.domain.RemoteLockEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.model.DBCollectionFindAndModifyOptions;

/**
 * 远程锁实现类
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月20日
 *
 */
@Component
@SuppressWarnings("unchecked")
public class RemoteLockDaoImpl extends MongoDaoImpl<RemoteLockEntity> {

	// 表名
	private static final String CollectionName = "_remoteLockEntity";
	// 数据集
	private DBCollection dbCollection;
	// 属性名
	private static final String PropertyName = "indexValue";
	// 需要建立索引的属性
	private static final String[] indexNames = new String[] { PropertyName, "uuid" };

	@Autowired
	private void init() {
		dbCollection = this.mongoTemplate.getCollection(CollectionName);
		// 创建索引
		for (String indexName : indexNames) {
			createIndex(indexName);
		}
	}

	/**
	 * 创建索引
	 */
	private void createIndex(final String indexName) {
		boolean isCreated = false;
		for (DBObject indexDb : this.dbCollection.getIndexInfo()) {
			Map<String, String> indexKeySet = (Map<String, String>) indexDb.get("key");
			if (indexKeySet != null && indexKeySet.size() > 0) {
				String indexKey = indexKeySet.keySet().toArray(new String[indexKeySet.size()])[0];
				if (indexName.equals(indexKey)) {
					isCreated = true;
					break;
				}
			}
		}
		if (!isCreated) {
			this.dbCollection.createIndex(indexName);
		}
	}

	/**
	 * 添加资源
	 * 
	 * @param serviceName
	 * @param name
	 * @return 抢资源成功则返回uuid
	 */
	public String add(String serviceName, String name) {
		// uuid
		String uuid = UUID.randomUUID().toString();

		// 资源
		String resourcesValue = createResourcesValue(serviceName, name);
		// 查询
		DBObject query = new BasicDBObject();
		query.put(PropertyName, resourcesValue);

		// 插入
		DBObject update = new BasicDBObject();
		DBObject data = new BasicDBObject().append("uuid", uuid).append(PropertyName, resourcesValue)
				.append("createTime", this.dateUpdateHelper.getDbTime());
		update.put("$setOnInsert", data);

		// 配置
		DBCollectionFindAndModifyOptions options = new DBCollectionFindAndModifyOptions();
		options.update(update);
		options.upsert(true);
		options.returnNew(true);
		DBObject dbObject = this.dbCollection.findAndModify(query, options);
		return uuid.equals(dbObject.get("uuid")) ? uuid : null;
	}

	/**
	 * 删除资源
	 * 
	 * @param serviceName
	 * @param name
	 */
	public String remove(String serviceName, String name) {
		// 资源
		String resourcesValue = createResourcesValue(serviceName, name);
		// 查询
		DBObject query = new BasicDBObject();
		query.put(PropertyName, resourcesValue);
		DBObject dbObject = this.dbCollection.findAndRemove(query);
		Object uuid = dbObject.get("uuid");
		return uuid == null ? null : String.valueOf(uuid);
	}

	/**
	 * 通过UUID删除该记录
	 */
	public void removeFromUUID(String uuid) {
		DBObject query = new BasicDBObject();
		query.put("uuid", uuid);
		this.dbCollection.remove(query);
	}

	/**
	 * 删除超时数据
	 */
	public void removeTimeout(long timeOut) {
		long time = this.dateUpdateHelper.getDbTime() - timeOut;
		DBObject query = new BasicDBObject().append("createTime", new BasicDBObject().append(QueryOperators.LT, time));
		this.dbCollection.remove(query);
	}

	/**
	 * 创建索引值
	 * 
	 * @param serviceName
	 * @param name
	 * @return
	 */
	private String createResourcesValue(String serviceName, String name) {
		return serviceName + "_" + name;
	}

}
