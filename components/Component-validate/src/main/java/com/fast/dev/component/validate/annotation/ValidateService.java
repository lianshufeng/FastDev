package com.fast.dev.component.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fast.dev.component.validate.service.Validator;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateService {
	/**
	 * 实现 ValidateService 接口的实例名，必须注入到spring中
	 * 
	 * @return
	 */
	Class<? extends Validator> value();
}
