package com.fast.dev.crawler.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fast.dev.crawler.CrawlerMain;
import com.fast.dev.crawler.core.ContentCrawler;
import com.fast.dev.crawler.core.Crawler;
import com.fast.dev.crawler.core.ListCrawler;
import com.fast.dev.crawler.core.PageCrawler;
import com.fast.dev.crawler.dao.ContentUrlsDao;
import com.fast.dev.crawler.dao.PageUrlsDao;
import com.fast.dev.crawler.dao.ResourcesDao;
import com.fast.dev.crawler.dao.TaskRecordDao;
import com.fast.dev.crawler.domain.ContentUrls;
import com.fast.dev.crawler.domain.PageUrls;
import com.fast.dev.crawler.model.ContentResult;
import com.fast.dev.crawler.model.UrlJob;

public class TimerTaskExecute implements Job {
	private static final Logger Log = Logger.getLogger(TimerTaskExecute.class);
	private PageUrlsDao pageUrlsDao;
	private TaskRecordDao taskRecordDao;
	private ContentUrlsDao contentUrlsDao;
	private ResourcesDao resourcesDao;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap jobDataMap = arg0.getMergedJobDataMap();
		Crawler crawler = (Crawler) jobDataMap.get("object");
		call(crawler);
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		if (this.pageUrlsDao == null) {
			this.pageUrlsDao = CrawlerMain.applicationContext.getBean(PageUrlsDao.class);
		}
		if (this.taskRecordDao == null) {
			this.taskRecordDao = CrawlerMain.applicationContext.getBean(TaskRecordDao.class);
		}
		if (this.contentUrlsDao == null) {
			this.contentUrlsDao = CrawlerMain.applicationContext.getBean(ContentUrlsDao.class);
		}
		if (this.resourcesDao == null) {
			this.resourcesDao = CrawlerMain.applicationContext.getBean(ResourcesDao.class);
		}
	}

	/**
	 * 调用爬虫对象
	 * 
	 * @param listCrawler
	 */
	private void call(Crawler crawler) {
		initDB();
		if (crawler instanceof PageCrawler) {
			callPageCrawler((PageCrawler) crawler);
		} else if (crawler instanceof ContentCrawler) {
			callContentCrawler((ContentCrawler) crawler);
		} else if (crawler instanceof ListCrawler) {
			callListCrawler((ListCrawler) crawler);
		}
	}

	/**
	 * 保存页面地址
	 * 
	 * @param crawler
	 */
	private void callPageCrawler(PageCrawler crawler) {
		String taskName = crawler.taskName();
		UrlJob[] sourcesUrls = crawler.pageUrls();
		if (this.taskRecordDao.exists(taskName)) {
			// 如果已经执行过则只保留几条记录
			sourcesUrls = crawler.repeat(sourcesUrls);
		}
		Log.info(String.format(" [%s] 加入页数 : [%s] ", taskName, sourcesUrls.length));
		for (UrlJob job : sourcesUrls) {
			this.pageUrlsDao.update(taskName, job.getUrl(), job.getData());
		}
	}

	/**
	 * 传递page页过去，返回content的list
	 * 
	 * @param crawler
	 */
	private void callListCrawler(ListCrawler crawler) {
		String taskName = crawler.taskName();
		PageUrls pageUrls = this.pageUrlsDao.findAndRemove(taskName);
		if (pageUrls != null) {
			String pageUrl = pageUrls.getUrl();
			Map<String, Object> data = pageUrls.getData();
			UrlJob[] contentUrls = crawler.call(pageUrl, data);
			if (contentUrls != null && contentUrls.length > 0) {
				for (UrlJob job : contentUrls) {
					this.contentUrlsDao.update(taskName, job.getUrl(), job.getData());
				}
				Log.info(String.format("获取 [%s] 列表 , 数量 : %s", pageUrl, contentUrls.length));
			}
		}
	}

	private final static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	/**
	 * 获取内容爬虫
	 * 
	 * @param crawler
	 */
	private void callContentCrawler(ContentCrawler crawler) {
		String taskName = crawler.taskName();
		ContentUrls contentUrls = this.contentUrlsDao.findAndRemove(taskName);
		if (contentUrls != null) {
			String url = contentUrls.getUrl();
			Map<String, Object> data = contentUrls.getData();
			ContentResult contentResult = crawler.call(url, data);
			if (contentResult != null) {
				String[] urls = contentResult.getUrls();
				if (urls != null && urls.length > 0) {
					for (String resUrl : urls) {
						this.resourcesDao.update(contentResult.getTitle(), resUrl, contentResult.getPublishTime());
						Log.info(String.format("标题 : [%s] , 地址 : [%s] , 时间 : [%s]", contentResult.getTitle(), resUrl,
								DateFormat.format(new Date(contentResult.getPublishTime()))));
					}
				}
			}
		}
	}

}
