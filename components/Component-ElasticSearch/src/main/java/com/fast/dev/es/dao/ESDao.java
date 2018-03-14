package com.fast.dev.es.dao;

import java.io.Serializable;
import java.util.Map;

public interface ESDao {

	/**
	 * 修改并保存对象
	 * 
	 * @param id
	 *            ， 为null则新增否则为修改
	 * @param source
	 * @return
	 */
	public Boolean update(String id, Serializable source);

	/**
	 * 批量更新
	 * 
	 * @param sources
	 * @return 如果value有值则为具体失败的原因
	 */
	public Map<String, String> update(Map<String, Serializable> sources);

	/**
	 * 保存文档
	 * 
	 * @param id
	 * @param sources
	 * @return
	 */
	public boolean save(String id, Serializable source);

	/**
	 * 修改并保存对象
	 * 
	 * @param source
	 * @return
	 */
	public String[] save(Serializable... sources);

	/**
	 * 获取文档对象
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, ?>[] get(String... id);

	/**
	 * 删除文档对象
	 * 
	 * @param id
	 */
	public void remove(String... id);

}
