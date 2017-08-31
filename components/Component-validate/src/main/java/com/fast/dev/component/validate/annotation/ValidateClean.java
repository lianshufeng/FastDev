package com.fast.dev.component.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
 * 清洗表达式
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年5月9日
 *
 *	例子：
 *
 *

	@ValidateClean({ 
			@Expression(where = "age==null", then = "age=18;info='年龄为空，自动纠正。' "),
			@Expression(where = "age<17", then = "age=18;info='年龄过小，矫正为18' ") ,
			@Expression(where = "age>100", then = "age=100;info='年龄过大，矫正为100' ") 
			})

 *
 *
 */
public @interface ValidateClean {


	/**
	 * Spring里容器的组件
	 * 
	 * @return
	 */
	Obj[] component() default {};

	
	
	/**
	 * 清洗，优先级高于过滤器
	 * @return
	 */
	Expression [] value() ;
	
}
