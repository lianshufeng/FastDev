package com.fast.dev.es.query;

/**
 * 高亮对象
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年3月15日
 *
 */
public class QueryHighlight {
	// 字段名
	private String fieldName;
	// 前缀
	private String preTag;
	// 后缀
	private String postTag;

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the preTag
	 */
	public String getPreTag() {
		return preTag;
	}

	/**
	 * @param preTag
	 *            the preTag to set
	 */
	public void setPreTag(String preTag) {
		this.preTag = preTag;
	}

	/**
	 * @return the postTag
	 */
	public String getPostTag() {
		return postTag;
	}

	/**
	 * @param postTag
	 *            the postTag to set
	 */
	public void setPostTag(String postTag) {
		this.postTag = postTag;
	}

	public QueryHighlight() {
		// TODO Auto-generated constructor stub
	}

	public QueryHighlight(String fieldName, String preTag, String postTag) {
		super();
		this.fieldName = fieldName;
		this.preTag = preTag;
		this.postTag = postTag;
	}

}
