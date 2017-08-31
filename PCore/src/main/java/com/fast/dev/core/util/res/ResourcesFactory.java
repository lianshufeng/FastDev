package com.fast.dev.core.util.res;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFactory {
	
	public final static List<UnpackJarModel> UnpackJarModels = new ArrayList<UnpackJarModel>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		{
			add(new UnpackJarModel(ResourcesType.classes, "classes/", "/WEB-INF/classes/", UnpackType.Skip));
			// 根目录的解压，重复不替换
			add(new UnpackJarModel(ResourcesType.root, "root/", "/", UnpackType.Skip));
			// 替换 jar包，jsp，资源文件
			add(new UnpackJarModel(ResourcesType.lib, "lib/", "/WEB-INF/lib/", UnpackType.Override));
			// jsp视图
			add(new UnpackJarModel(ResourcesType.views, "views/", "/WEB-INF/views/", UnpackType.Override));
			// freemark视图
			add(new UnpackJarModel(ResourcesType.ftl, "ftl/", "/WEB-INF/ftl/", UnpackType.Override));
			// groovymark视图模版
			add(new UnpackJarModel(ResourcesType.gtl, "gtl/", "/WEB-INF/gtl/", UnpackType.Override));
			// velocitymark视图模版
			add(new UnpackJarModel(ResourcesType.vtl, "vtl/", "/WEB-INF/vtl/", UnpackType.Override));
			// thymeleaf 视图模版
			add(new UnpackJarModel(ResourcesType.htl, "htl/", "/WEB-INF/htl/", UnpackType.Override));
			// 静态资源
			add(new UnpackJarModel(ResourcesType.resources, "resources/", "/resources/", UnpackType.Override));
		}
	};
}
