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
 * 过滤表达式
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年5月9日
 *	
 *	例子：
 *
 *
 *

@ValidateFilter(
		result = @Obj(v = "user", cls = User.class) ,
		value = @Expression(where = "userName == null ", then = "user.info = '账号为空' ") 
		)
 *
 * 
 */
public @interface ValidateFilter {

	/**
	 * 返回的结果
	 * 
	 * @return
	 */
	Obj result();

	/**
	 * Spring里容器的组件
	 * 
	 * @return
	 */
	Obj[]component() default {};

	/**
	 * 过滤器，会替代原有的方法
	 * 
	 * @return
	 */
	Expression [] value();

}
