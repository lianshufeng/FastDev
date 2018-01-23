package com.fast.dev.component.mongodb.lock.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fast.dev.component.mongodb.dao.domain.SuperEntity;

/**
 * 资源锁实体
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年1月20日
 *
 */
@Document(collection="_remoteLockEntity")
public class RemoteLockEntity extends SuperEntity {

	// 资源数据
	@Indexed(unique = true)
	private String indexValue;

	// uuid
	private String uuid;

	/**
	 * @return the indexValue
	 */
	public String getIndexValue() {
		return indexValue;
	}

	/**
	 * @param indexValue
	 *            the indexValue to set
	 */
	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
