package com.fast.dev.crawler.timer;

import java.util.Timer;
import java.util.TimerTask;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fast.dev.crawler.CrawlerMain;
import com.fast.dev.crawler.core.ListCrawler;
import com.fast.dev.crawler.dao.CrawlerTaskDao;
import com.fast.dev.crawler.domain.TimerListTask;
import com.fast.dev.crawler.model.ListCrawlerParameter;
import com.fast.dev.crawler.model.ListCrawlerResult;
import com.fast.dev.crawler.model.ListNextPageAndUrlsResult;
import com.fast.dev.crawler.model.ListUrlsResult;

public class TimerTaskExecute implements Job {

	private CrawlerTaskDao taskDao;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap jobDataMap = arg0.getMergedJobDataMap();
		ListCrawler listCrawler = (ListCrawler) jobDataMap.get("object");
		call(listCrawler);
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		if (this.taskDao == null) {
			this.taskDao = CrawlerMain.applicationContext.getBean(CrawlerTaskDao.class);
		}
	}

	/**
	 * 处理结果集
	 * 
	 * @param result
	 */
	private void listUrlsResult(ListCrawler listCrawler, ListUrlsResult result) {

	}

	/**
	 * 处理结果集
	 * 
	 * @param taskName
	 * @param result
	 */
	private void listNextPageAndUrlsResult(final ListCrawler listCrawler, ListNextPageAndUrlsResult result) {
		this.taskDao.updateTimerTask(listCrawler.name(), result.getEndInfo(), result.getNextPage());
		// 未执行完成，有下一页
		if (result.getNextPage() != null) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					call(listCrawler);
				}
			}, result.getNextPageSleepTime());
		}

	}

	/**
	 * 调用爬虫对象
	 * 
	 * @param listCrawler
	 */
	private void call(ListCrawler listCrawler) {
		initDB();
		String taskName = listCrawler.name();
		// 取调用参数
		ListCrawlerParameter listCrawlerParameter = getListCrawlerParameter(taskName);
		// 调用接口
		ListCrawlerResult result = listCrawler.call(listCrawlerParameter);
		if (result == null) {
			return;
		}
		if (result instanceof ListNextPageAndUrlsResult) {
			listNextPageAndUrlsResult(listCrawler, (ListNextPageAndUrlsResult) result);
		} else if (result instanceof ListUrlsResult) {
			listUrlsResult(listCrawler, (ListUrlsResult) result);
		}
	}

	/**
	 * 获取调用者参数
	 * 
	 * @param taskName
	 * @return
	 */
	private ListCrawlerParameter getListCrawlerParameter(String taskName) {
		// 取出参数
		TimerListTask timerListTask = this.taskDao.findAndRemoveTimerTask(taskName);
		return timerListTask == null ? new ListCrawlerParameter()
				: new ListCrawlerParameter(timerListTask.getEndInfo(), timerListTask.getNextPage());
	}

}
