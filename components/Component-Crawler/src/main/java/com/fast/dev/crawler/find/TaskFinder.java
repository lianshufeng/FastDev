package com.fast.dev.crawler.find;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.crawler.core.Crawler;
import com.fast.dev.crawler.timer.TimerManager;

import groovy.lang.GroovyClassLoader;

@Component
public class TaskFinder {
	private static final Logger Log = Logger.getLogger(TaskFinder.class);
	// 根目录
	private String rootPath = TaskFinder.class.getResource("/").getPath() + "scripts/";
	Timer timer = new Timer();
	private Map<String, Long> filesCache = new ConcurrentHashMap<>();

	private Map<String, Crawler> crawlerCache = new ConcurrentHashMap<>();

	@Autowired
	private TimerManager timerManager;

	@PreDestroy
	private void shutdown() {
		this.timer.cancel();
	}

	@Autowired
	private void init() {
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				find();
			}
		}, 1000, 5000);
	}

	private void find() {
		File directory = new File(rootPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		if (!directory.isDirectory()) {
			return;
		}
		// 寻找所有资源
		Collection<File> files = FileUtils.listFiles(new File(rootPath), new IOFileFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return false;
			}

			@Override
			public boolean accept(File file) {
				String extName = FilenameUtils.getExtension(file.getName());
				if ("java".equalsIgnoreCase(extName) || "groovy".equalsIgnoreCase(extName)) {
					return true;
				}
				return false;
			}
		}, new IOFileFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return true;
			}

			@Override
			public boolean accept(File file) {
				return true;
			}
		});

		// 删除老任务
		removeOldTask(files);

		// 寻找更新的任务
		for (File file : files) {
			String filePath = file.getAbsolutePath();
			boolean needUpdate = false;
			// 是否存在
			if (this.filesCache.containsKey(filePath)) {
				// 文件修改时间
				if (this.filesCache.get(filePath) != file.lastModified()) {
					needUpdate = true;
				}
			} else {
				needUpdate = true;
			}
			if (needUpdate) {
				updateTask(file);
				this.filesCache.put(filePath, file.lastModified());
			}
		}
	}

	/**
	 * 删除旧任务
	 * 
	 * @param files
	 */
	private void removeOldTask(final Collection<File> files) {
		List<File> deleteDFiles = new ArrayList<>();
		for (String filePath : this.filesCache.keySet()) {
			File file = new File(filePath);
			if (!files.contains(file)) {
				deleteDFiles.add(file);
				this.filesCache.remove(filePath);
			}
		}
		if (deleteDFiles.size() > 0) {
			for (File delFile : deleteDFiles) {
				String filePath = delFile.getAbsolutePath();
				// 删除缓存对象
				Crawler crawler = this.crawlerCache.remove(filePath);
				if (crawler != null) {
					Log.info("delete:" + filePath);
					// 删除调度器
					this.timerManager.remove(filePath);

				}
			}
		}
	}

	/**
	 * 更新任务
	 * 
	 * @param file
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	private void updateTask(File file) {
		Log.info("update:" + file);
		try {
			// 默认的类加载器
			GroovyClassLoader loader = new GroovyClassLoader();
			Class<Crawler> groovyClass = loader.parseClass(file);
			Crawler crawler = groovyClass.newInstance();
			if (crawler != null) {
				updateCrawler(file, crawler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新列表爬虫任务
	 * 
	 * @param file
	 * @param listCrawler
	 */
	private void updateCrawler(final File file, final Crawler crawler) {
		String filePath = file.getAbsolutePath();
		// 调度器
		String corn = crawler.corn();
		// 增加到调度器里
		this.timerManager.add(filePath, corn, crawler);
		this.crawlerCache.put(filePath, crawler);
	}

}
