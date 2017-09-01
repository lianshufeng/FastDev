package com.fast.dev.tools.autodeployment.setup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fast.dev.core.util.bytes.BytesUtil;
import com.fast.dev.tools.autodeployment.model.ProjectMoudel;
import com.fast.dev.tools.autodeployment.thread.ListenResourcesTask;

/**
 * 自动部署并启动，无需外部调用,并且必须与PServer连用
 * 
 * @作者 练书锋
 * @时间 2016年4月5日
 */
@Component
public class InstallAutoDeployment {
	static Log log = LogFactory.getLog(InstallAutoDeployment.class);

	@Autowired
	private ApplicationContext applicationContext;

	// 工作空间
	private String workSpacePath;
	// 项目空间
	private String projectPath;
	// 项目容器
	List<ProjectMoudel> projectMoudels = new ArrayList<ProjectMoudel>();

	// 线程池
	private ExecutorService executorService;

	@PostConstruct
	private void init() throws IOException {
		// 初始化路径
		initPath();
		// 初始化项目
		initProject();
		// 初始化线程
		initThread();
	}

	@PreDestroy
	private void shutdown() {
		if (this.executorService != null) {
			this.executorService.shutdownNow();
		}
	}

	// 初始化线程池
	private void initThread() {
		// 实例化线程池
		this.executorService = Executors.newFixedThreadPool(projectMoudels.size());
		// 监视模块更新
		for (ProjectMoudel pm : this.projectMoudels) {
			log.info("监视模块：" + pm.getProjectName());
			ListenResourcesTask listenResourcesTask = this.applicationContext.getBean(ListenResourcesTask.class);
			listenResourcesTask.setPath(new File(pm.getLocation()));
			this.executorService.execute(listenResourcesTask);
		}
	}

	/**
	 * 初始化路径
	 * 
	 * @throws IOException
	 */
	private void initPath() throws IOException {
		// 工作空间
		workSpacePath = this.applicationContext.getResource(".").getFile().getAbsolutePath();
		workSpacePath = workSpacePath.replaceAll("\\\\", "/");
		int endPath = workSpacePath.lastIndexOf("/.metadata/.plugins/org.eclipse.wst.server.core/");
		// 项目空间
		projectPath = workSpacePath.substring(0, endPath + 1);
	}

	/**
	 * 初始化项目
	 * 
	 * @throws IOException
	 */
	private void initProject() throws IOException {
		// 项目名称
		File[] projectNames = readProjects();
		for (File f : projectNames) {
			File locationFile = new File(f.getAbsolutePath() + "/.location");
			String location = null;
			if (locationFile.exists()) {
				location = readLocationFile(locationFile);
			} else if (new File(this.projectPath + "/" + f.getName() + "/pom.xml").exists()) {
				location = this.projectPath + "/" + f.getName();
			}
			// 添加有效项目
			if (location != null) {
				projectMoudels.add(new ProjectMoudel(f.getName(), location));
			}
		}

	}

	/**
	 * 读取location文件里获取具体路径
	 * 
	 * @param locationFile
	 * @return
	 * @throws IOException
	 */
	private static String readLocationFile(File locationFile) throws IOException {
		byte[] buff = FileUtils.readFileToByteArray(locationFile);
		// 标记长度的字节
		byte[] lenBuff = ArrayUtils.subarray(buff, 16, 18);
		int locationSizle = Integer.parseInt(BytesUtil.binToHex(lenBuff), 16);
		// 路径的字节
		byte[] locationBuff = ArrayUtils.subarray(buff, 18, 18 + locationSizle);
		String location = new String(locationBuff);
		// 过滤文件开始符号
		String fileFlag = "file:";
		int fileAt = location.indexOf(fileFlag);
		location = location.substring(fileAt + fileFlag.length(), location.length());
		return location;
	}

	// 通过eclipse插件配置信息读取当前工作空间的项目列表
	private File[] readProjects() {
		String projectConfig = this.projectPath + "/.metadata/.plugins/org.eclipse.core.resources/.projects";
		return new File(projectConfig).listFiles();
	}

}
