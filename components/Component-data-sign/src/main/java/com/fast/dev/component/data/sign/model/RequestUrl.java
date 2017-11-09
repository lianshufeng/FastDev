package com.fast.dev.component.data.sign.model;

import java.io.Serializable;

/**
 * 请求的URL配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月5日
 *
 */
public class RequestUrl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 需要拦截方法的URL
	private String[] addPathPatterns = null;

	// 在拦截的URL中排除不拦截的URL
	private String[] excludePathPatterns = null;

	// 执行拦截URL的优先级
	private int level = 0;

	/**
	 * @return the addPathPatterns
	 */
	public String[] getAddPathPatterns() {
		return addPathPatterns;
	}

	/**
	 * @param addPathPatterns
	 *            the addPathPatterns to set
	 */
	public void setAddPathPatterns(String[] addPathPatterns) {
		this.addPathPatterns = addPathPatterns;
	}

	/**
	 * @return the excludePathPatterns
	 */
	public String[] getExcludePathPatterns() {
		return excludePathPatterns;
	}

	/**
	 * @param excludePathPatterns
	 *            the excludePathPatterns to set
	 */
	public void setExcludePathPatterns(String[] excludePathPatterns) {
		this.excludePathPatterns = excludePathPatterns;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
