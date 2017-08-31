package com.fast.dev.core.environments;

import javax.servlet.http.HttpServletRequest;

import com.fast.dev.core.util.net.RequestUtil;

public class ENVFactory {

	/**
	 * 添加环境变量
	 * 
	 * @param request
	 * @param modelAndView
	 */
	public static ENVModel create(HttpServletRequest request) {
		String requestUrl = RequestUtil.getUrl(request);
		ENVModel envModel = new ENVModel();
		envModel.setRoot(requestUrl);
		// 如果要迁移资源服务器，则修改此处的地址为资源域名
		envModel.setResources(requestUrl + "/" + ENVPath.RESOURCES);
		envModel.setTime(System.currentTimeMillis());
		return envModel;
	}

}
