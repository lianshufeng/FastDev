package com.fast.dev.tools.autodeployment.thread;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fast.dev.core.res.ResourcesFactory;
import com.fast.dev.core.res.UnpackJarModel;
import com.fast.dev.core.res.UnpackType;
import com.fast.dev.core.util.code.Crc32Util;
import com.fast.dev.core.util.net.UrlUtil;

@Component
@Scope("prototype")
public class ListenResourcesTask implements Runnable {

	static Log log = LogFactory.getLog(ListenResourcesTask.class);

	@Autowired
	private ApplicationContext applicationContext;

	private String projectPath;

	protected Timer timer = new Timer();

	private File path;

	// 缓存资源
	private Map<File, Long> resourcesCacheMap = new ConcurrentHashMap<File, Long>();
	// 缓存解压路径
	private Map<String, UnpackJarModel> unpackJarModelMap = new HashMap<String, UnpackJarModel>();

	// 将要更新的文件列表
	private Vector<File> needUpdateFile = new Vector<File>();

	// 资源路径
	protected String resourcesPath;

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	/**
	 * 初始化存储对象
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	private void initTask() throws IOException {
		// 工作空间
		projectPath = this.applicationContext.getResource(".").getFile().getAbsolutePath();
		// 解压包模块路径
		for (UnpackJarModel unpackJarModel : ResourcesFactory.UnpackJarModels) {
			unpackJarModelMap.put(unpackJarModel.getSource(), unpackJarModel);
		}
	}

	@PreDestroy
	private void shutdown() {
		this.timer.cancel();
	}

	@Override
	public void run() {

		this.resourcesPath = UrlUtil.format(path.getAbsolutePath() + "/src/main/resources/");

		// 检查更新的资源
		this.timer.scheduleAtFixedRate(new CheckUpdateResources(this), 5000, 1000);

		// 更新新资源
		this.timer.scheduleAtFixedRate(new UpdateResources(this), 5000, 300);

	}

	/**
	 * 取相对路径
	 * 
	 * @param work
	 * @param file
	 * @return
	 */
	private String getRelativepath(File work, File file) {
		String workPath = work.getAbsolutePath();
		String filePath = file.getAbsolutePath();
		return filePath.substring(workPath.length(), filePath.length());
	}

	// 更新资源到项目空间里
	protected void updateResourcesToProject() throws IOException {
		if (this.needUpdateFile.size() > 0) {
			// 原路径
			File sourcesFile = this.needUpdateFile.remove(0);
			// 取出相对路径
			String source = UrlUtil.format(getRelativepath(new File(resourcesPath), sourcesFile));
			// 第一个字符不允许出现 / 符号
			source = source.substring(0, 1).equals("/") ? source.substring(1, source.length()) : source;
			String[] sourceArray = source.split("/");
			if (sourceArray.length > 0) {
				// 匹配找到对应的解压模块
				String sourceHead = sourceArray[0];
				String[] sourceBody = ArrayUtils.subarray(sourceArray, 1, sourceArray.length);
				UnpackJarModel unpackJarModel = this.unpackJarModelMap.get(sourceHead);
				if (unpackJarModel != null && unpackJarModel.getUnpackType() == UnpackType.Override) {
					// 目标相对路径
					StringBuilder sb = new StringBuilder();
					for (String bodyOne : sourceBody) {
						sb.append(bodyOne + "/");
					}
					// 目标的绝对路径
					File targetFile = new File(projectPath + "/" + unpackJarModel.getTarget() + "/" + sb.toString());
					// 拷贝文件
					copyFile(sourcesFile, targetFile);
				}
			}
		}
	}

	// 拷贝文件
	private static void copyFile(File source, File target) throws IOException {
		boolean needCopy = false;
		if (target.exists()) {
			needCopy = Crc32Util.hash(FileUtils.readFileToByteArray(source)) != Crc32Util
					.hash(FileUtils.readFileToByteArray(target));
		} else {
			needCopy = true;
		}

		if (needCopy) {
			FileUtils.copyFile(source, target);
			log.info("自动发布 ：" + source.getName());
		}

	}

	// 检查资源是否需要更新
	protected void checkUpdateResources() {
		File target = new File(this.resourcesPath);
		if (!target.exists() || !target.isDirectory()) {
			return;
		}
		Collection<File> files = FileUtils.listFiles(target, new IOFileFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return true;
			}

			@Override
			public boolean accept(File file) {
				return true;
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

		// 判断类型并放入相应的缓存队列里
		for (File file : files) {
			if (file.isFile()) {
				Long updateTime = this.resourcesCacheMap.get(file);
				// 新文件或者文件被修改
				if (updateTime == null || updateTime != file.lastModified()) {
					if (!this.needUpdateFile.contains(file)) {
						this.needUpdateFile.add(file);
					}
					this.resourcesCacheMap.put(file, file.lastModified());
				}

			}
		}
	}

}

/**
 * 检查新增资源
 * 
 * @作者 练书锋
 * @时间 2016年4月6日
 */
class CheckUpdateResources extends TimerTask {

	private ListenResourcesTask listenResourcesTask;

	public CheckUpdateResources(ListenResourcesTask listenResourcesTask) {
		super();
		this.listenResourcesTask = listenResourcesTask;
	}

	@Override
	public void run() {
		listenResourcesTask.checkUpdateResources();
	}
}

/**
 * 检查更新资源
 * 
 * @作者 练书锋
 * @时间 2016年4月6日
 */
class UpdateResources extends TimerTask {

	private ListenResourcesTask listenResourcesTask;

	public UpdateResources(ListenResourcesTask listenResourcesTask) {
		super();
		this.listenResourcesTask = listenResourcesTask;
	}

	@Override
	public void run() {
		try {
			this.listenResourcesTask.updateResourcesToProject();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
