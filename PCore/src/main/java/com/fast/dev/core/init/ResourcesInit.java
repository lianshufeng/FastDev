package com.fast.dev.core.init;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fast.dev.core.res.ResourcesFactory;
import com.fast.dev.core.res.ResourcesUtil;

/**
 * 资源包初始化，解压jar包中的资源到web项目里
 * 
 * @author 练书锋
 *
 */
public class ResourcesInit {

	 static Log log = LogFactory.getLog(ResourcesInit.class);

	/**
	 * 初始化资源
	 * 
	 * @param realPath
	 */
	public static void init(final String realPath, String packageName) {
		log.info("初始项目：" + realPath);
		ResourcesFactory.init(packageName);
		// 取出所有的jar包
		List<File> jarFiles = getJars(realPath);
		for (File jarFile : jarFiles) {
			// 开始解压资源文件
			ResourcesUtil.unpack(jarFile, new File(realPath), ResourcesFactory.UnpackJarModels);
		}

	}

	/**
	 * 获取指定目录下的jar包
	 * 
	 * @param realPath
	 * @return
	 */
	private static List<File> getJars(String realPath) {
		List<File> jarList = new ArrayList<File>();
		File libFile = new File(realPath + "/WEB-INF/lib/");
		for (File file : libFile.listFiles()) {
			if (file.isFile()) {
				String filePath = file.getAbsolutePath();
				if (".jar".equalsIgnoreCase(filePath.substring(filePath.length() - 4, filePath.length()))) {
					jarList.add(file);
				}
			}
		}
		return jarList;
	}

}
