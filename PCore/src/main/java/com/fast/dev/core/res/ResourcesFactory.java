package com.fast.dev.core.res;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.fast.dev.core.util.scan.ClassesScanner;

/**
 * 初始化
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年8月31日
 *
 */
@Component
public class ResourcesFactory {

	static Log log = LogFactory.getLog(ResourcesFactory.class);

	public final static List<UnpackJarModel> UnpackJarModels = new ArrayList<UnpackJarModel>();

	/**
	 * 取所有资源解压模块
	 * 
	 * @return
	 */
	public static void init(String packageName) {
		UnpackJarModels.clear();
		try {
			List<String> classSimpleName = new ArrayList<String>();
			for (String className : ClassesScanner.scanComponents(packageName)) {
				Class<?> cls = Class.forName(className);
				if (UnpackJarModel.class.isAssignableFrom(cls)) {
					UnpackJarModel unpackJarModel = (UnpackJarModel) cls.newInstance();
					UnpackJarModels.add(unpackJarModel);
					classSimpleName.add(cls.getSimpleName());
				}
			}
			log.info("unpack : " + classSimpleName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
