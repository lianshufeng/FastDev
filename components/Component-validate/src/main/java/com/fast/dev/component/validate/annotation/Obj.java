package com.fast.dev.component.validate.annotation;

public @interface Obj {

	/**
	 * variable 变量名
	 * 
	 * @return
	 */
	String v();

	/**
	 * class 对象类型 
	 */
	Class<?> cls();

}
