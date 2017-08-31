package com.fast.dev.component.validate.annotation;

public @interface Expression {
	/**
	 * 表达式脚本
	 * 
	 * @return
	 */
	String where();

	/**
	 * 返回结果的表达式脚本
	 * 
	 * @return
	 */
	String then();

}
