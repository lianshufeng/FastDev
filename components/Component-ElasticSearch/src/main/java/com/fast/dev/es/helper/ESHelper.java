package com.fast.dev.es.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.fast.dev.es.dao.ESDao;
import com.fast.dev.es.dao.impl.ESDaoImpl;

/**
 * ES助手
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月14日
 *
 */
public class ESHelper {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 创建Dao
	 * 
	 * @return
	 */
	public ESDao dao(String indexName, String typeName) {
		ESDaoImpl esDaoImpl = this.applicationContext.getBean(ESDaoImpl.class);
		esDaoImpl.config(indexName, typeName);
		return esDaoImpl;
	}

}
