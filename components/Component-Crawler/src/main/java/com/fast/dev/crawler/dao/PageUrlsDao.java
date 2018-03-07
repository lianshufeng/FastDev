package com.fast.dev.crawler.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fast.dev.crawler.domain.PageUrls;

@Component
public class PageUrlsDao extends UrlsDao<PageUrls> {

	/**
	 * 此条URL是否已存在
	 * 
	 * @param url
	 * @return
	 */
	public boolean existsByUrl(String url) {
		return this.mongoTemplate.exists(new Query().addCriteria(Criteria.where("url").is(url)), entityClass);
	}



}
