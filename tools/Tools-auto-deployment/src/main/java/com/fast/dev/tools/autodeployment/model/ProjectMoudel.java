package com.fast.dev.tools.autodeployment.model;

/**
 * 项目模块
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年9月1日
 *
 */
public class ProjectMoudel {

	// 项目名
	private String projectName;
	// 路径
	private String location;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ProjectMoudel() {
		// TODO Auto-generated constructor stub
	}

	public ProjectMoudel(String projectName, String location) {
		super();
		this.projectName = projectName;
		this.location = location;
	}

}
