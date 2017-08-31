package com.fast.dev.core.environments;

/**
 * 环境变量的model
 * 
 * @作者 练书锋
 * @时间 2016年7月8日
 *
 */
public class ENVModel {
	// 根路径
	private String root;
	// 资源路径
	private String resources;
	// 服务器时间
	private long time;
	//版本号
	private ResourceVersionModel ver;
	

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ResourceVersionModel getVer() {
		return ver;
	}

	public void setVer(ResourceVersionModel ver) {
		this.ver = ver;
	}
	
	

}
