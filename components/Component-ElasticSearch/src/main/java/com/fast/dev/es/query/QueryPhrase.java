package com.fast.dev.es.query;

import java.util.Collection;

/**
 * 短语查询(模糊查询)
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryPhrase {
	// 匹配集合
	private Collection<QueryMatch> queryMatch;

	// 是否用and组合条件，否则用or
	private boolean and;

	public Collection<QueryMatch> getQueryMatch() {
		return queryMatch;
	}

	public void setQueryMatch(Collection<QueryMatch> queryMatch) {
		this.queryMatch = queryMatch;
	}

	public boolean isAnd() {
		return and;
	}

	public void setAnd(boolean and) {
		this.and = and;
	}

	public QueryPhrase(Collection<QueryMatch> queryMatch, boolean and) {
		super();
		this.queryMatch = queryMatch;
		this.and = and;
	}

	public QueryPhrase() {
		// TODO Auto-generated constructor stub
	}

}
