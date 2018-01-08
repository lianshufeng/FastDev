package com.fast.dev.component.mongodb.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBTimerHelper {

	@Autowired
	private MongoTemplate mongoTemplate;

	// 请求任务时间
	private RequestTimeTask requestTimeTask = new RequestTimeTask();
	// 线程池
	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	// 获取DB时间的脚本
	private final static String DBScripr = "function(){return new Date().getTime().toString()}";

	@PreDestroy
	private void shutdown() {
		threadPool.shutdownNow();
	}

	/**
	 * 获取DB时间
	 * 
	 * @return
	 */
	public long getDBTime() {
		startRequestTimeTask();
		return getlocalTime() + this.requestTimeTask.getDBOffSetTime();
	}

	/**
	 * 启动请求时间任务
	 * 
	 * @param isThread
	 */
	private void startRequestTimeTask() {
		if (this.requestTimeTask.isInitSuccess()) {
			if (this.requestTimeTask.isTimeOut()) {
				this.threadPool.execute(this.requestTimeTask);
			}
		} else {
			this.requestTimeTask.run();
		}
	}

	/**
	 * 获取本地时间
	 */
	private long getlocalTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 请求数据库时间
	 * 
	 * @return
	 */
	protected long requestDBTime() {
		return Long.valueOf(String.valueOf(this.mongoTemplate.getDb().eval(DBScripr)));
	}

	/**
	 * 请求时间任务
	 * 
	 * @作者 练书锋
	 * @联系 251708339@qq.com
	 * @时间 2018年1月5日
	 *
	 */
	class RequestTimeTask implements Runnable {

		// 最后一次访问时间
		private Long lastAccessTime = 0l;

		// 时间偏移
		private long offSetTime = 0;

		// 是否工作中
		private Boolean working = false;

		// 已初始化
		private boolean initSuccess = false;

		/**
		 * 是否超时
		 * 
		 * @return
		 */
		public boolean isTimeOut() {
			//默认缓存1000
			return System.currentTimeMillis() - lastAccessTime > 1000 * 60 * 60;
		}

		/**
		 * 是否初始化成功
		 * 
		 * @return
		 */
		public boolean isInitSuccess() {
			return this.initSuccess;
		}

		/**
		 * 获取偏移时间
		 * 
		 * @return
		 */
		public Long getDBOffSetTime() {
			return offSetTime;
		}

		/**
		 * 是否正在工作中
		 * 
		 * @return
		 */
		public boolean isWork() {
			return this.working;
		}

		@Override
		public void run() {

			// 如果没有超时则不执行
			if (!isTimeOut() && isInitSuccess()) {
				return;
			}

			// 过滤重复的并发
			if (working) {
				return;
			}
			working = true;

			try {
				offSetTime = DBTimerHelper.this.requestDBTime() - System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lastAccessTime = System.currentTimeMillis();
				working = false;
				initSuccess = true;
			}

		}

	}

}
