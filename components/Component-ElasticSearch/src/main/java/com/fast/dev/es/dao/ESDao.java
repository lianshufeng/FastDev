package com.fast.dev.es.dao;

import java.util.Collection;
import java.util.Map;

import org.elasticsearch.index.query.QueryBuilder;

import com.fast.dev.es.query.QueryHighlight;
import com.fast.dev.es.query.QueryLimit;
import com.fast.dev.es.query.QueryPhrase;
import com.fast.dev.es.query.QueryResult;
import com.fast.dev.es.query.QuerySort;

public interface ESDao {

	/**
	 * 修改并保存对象
	 * 
	 * @param id
	 *            ， 为null则新增否则为修改
	 * @param source
	 * @return
	 */
	public Boolean update(String id, Object source);

	/**
	 * 批量更新
	 * 
	 * @param sources
	 * @return 如果value有值则为具体失败的原因
	 */
	public Map<String, String> update(Map<String, Object> sources);

	/**
	 * 保存文档
	 * 
	 * @param id
	 * @param sources
	 * @return
	 */
	public boolean save(String id, Object source);

	/**
	 * 修改并保存对象
	 * 
	 * @param source
	 * @return 如果非null则为失败原因
	 */
	public Map<String, String> save(Object... sources);

	/**
	 * 获取文档对象
	 * 
	 * @param id
	 * @return 失败返回 null
	 */
	public Map<String, Object> get(String... ids);

	/**
	 * 删除文档对象
	 * 
	 * @param id
	 */
	public Map<String, String> remove(String... ids);

	/**
	 * 分页查询
	 * 
	 * @param queryPhrases
	 *            多条查询规则用or连接
	 * @param queryHighlights
	 *            高亮规则
	 * @param sorts
	 *            排序
	 * @param queryLimit
	 *            分页限制
	 * @return
	 */
	public QueryResult list(QueryPhrase queryPhrase, Collection<QueryHighlight> queryHighlights,
			Collection<QuerySort> sorts, QueryLimit queryLimit);

	/**
	 * 查询
	 * 
	 * @param queryBuilder
	 *            自定义查询
	 * @param queryHighlights
	 * @param sorts
	 * @param queryLimit
	 * @return
	 */
	public QueryResult list(QueryBuilder queryBuilder, Collection<QueryHighlight> queryHighlights,
			Collection<QuerySort> sorts, QueryLimit queryLimit);

}
