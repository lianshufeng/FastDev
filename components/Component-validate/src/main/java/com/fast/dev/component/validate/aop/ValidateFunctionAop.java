package com.fast.dev.component.validate.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fast.dev.component.validate.annotation.Expression;
import com.fast.dev.component.validate.annotation.Obj;
import com.fast.dev.component.validate.annotation.ValidateClean;
import com.fast.dev.component.validate.annotation.ValidateFilter;
import com.fast.dev.component.validate.annotation.ValidateService;
import com.fast.dev.component.validate.result.ValidateResult;
import com.fast.dev.component.validate.service.Validator;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.Script;

@Aspect
@Component
public class ValidateFunctionAop {
	// 日志对象
	private static Log log = LogFactory.getLog(ValidateFunctionAop.class);

	private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader(
			ValidateFunctionAop.class.getClassLoader());
	// 缓存groovy的脚本对象
	private static Map<String, Script> scriptCache = new ConcurrentHashMap<String, Script>();
	// spring 缓存对象
	private static Map<Class<?>, Object> springContentCache = new ConcurrentHashMap<Class<?>, Object>();

	// 缓存校验接口
	private Map<Class<? extends Validator>, Validator> cacheValidateServiceMap = new HashMap<Class<? extends Validator>, Validator>();

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	private void init() {
		Collection<? extends Validator> validators = this.applicationContext.getBeansOfType(Validator.class).values();
		for (Validator validator : validators) {
			cacheValidateServiceMap.put(validator.getClass(), validator);
		}
		log.info("Cache Validator : " + this.cacheValidateServiceMap.keySet());
	}

	@Pointcut("@annotation(com.fast.dev.component.validate.annotation.ValidateFilter) ")
	public void validateFilter() {
	}

	@Pointcut("@annotation(com.fast.dev.component.validate.annotation.ValidateClean) ")
	public void validateClean() {
	}

	@Pointcut("@annotation(com.fast.dev.component.validate.annotation.ValidateService) ")
	public void validateService() {
	}

	@Around("validateClean()")
	public Object aroundValidateClean(ProceedingJoinPoint pjp) throws Throwable {
		// 变量对象
		Object[] args = pjp.getArgs();
		// 表达式注解对象不能为空
		ValidateClean validateClean = getMethodAnnotation(pjp, ValidateClean.class);
		if (validateClean != null) {
			// 表达式脚本不能为空
			Expression[] expressions = validateClean.value();
			if (expressions != null && expressions.length > 0) {
				MethodSignature signature = (MethodSignature) pjp.getSignature();
				// 参数名列表
				String[] parameterNames = signature.getParameterNames();
				// spring 的组件对象
				Obj[] components = validateClean.component();
				// 脚本对象，并注入参数变量
				Binding binding = new Binding();
				// 设置bing变量
				setBind(binding, parameterNames, args, components, null);
				// 执行表达式
				executeExpression(binding, expressions, -1);
				// 重新赋值参数
				for (int i = 0; i < parameterNames.length; i++) {
					String pName = parameterNames[i];
					Object pValue = binding.getVariable(pName);
					args[i] = pValue;
				}
			}
		}
		return pjp.proceed(args);
	}

	@Around("validateFilter()")
	public Object aroundValidateFilter(ProceedingJoinPoint pjp) throws Throwable {
		ValidateFilter validateFilter = getMethodAnnotation(pjp, ValidateFilter.class);
		// 表达式注解对象不能为空
		if (validateFilter != null) {
			// 表达式脚本不能为空
			Expression[] expressions = validateFilter.value();
			if (expressions != null && expressions.length > 0) {
				MethodSignature signature = (MethodSignature) pjp.getSignature();
				// 返回对象
				Obj result = validateFilter.result();
				// 参数名列表
				String[] parameterNames = signature.getParameterNames();
				// 变量对象
				Object[] args = pjp.getArgs();
				// spring 的组件对象
				Obj[] components = validateFilter.component();
				// 脚本对象，并注入参数变量
				Binding binding = new Binding();
				// 设置bing变量
				setBind(binding, parameterNames, args, components, result);
				// 执行表达式
				if (executeExpression(binding, expressions, 1) > 0) {
					// 返回对象
					return result.cls() != void.class ? binding.getVariable(result.v()) : null;
				}
			}
		}
		return pjp.proceed();
	}

	@Around("validateService()")
	public Object aroundValidateService(ProceedingJoinPoint pjp) throws Throwable {
		ValidateService validateService = getMethodAnnotation(pjp, ValidateService.class);
		if (validateService != null) {
			// 通过缓存取得接口实现
			Validator validator = this.cacheValidateServiceMap.get(validateService.value());
			if (validator != null) {
				ValidateResult validateResult = new ValidateResult();
				// 调用校验器
				validator.validate(pjp.getArgs(), validateResult);
				// 重置结果
				if (validateResult.getResult() != null) {
					return validateResult.getResult();
				}
			}
		}
		return pjp.proceed();
	}

	/**
	 * 获取切点方法的注解
	 * 
	 * @param pjp
	 * @return
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T getMethodAnnotation(ProceedingJoinPoint pjp, Class<T> t) {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		Annotation annotation = method.getAnnotation((Class) t);
		return (T) annotation;
	}

	/**
	 * 执行表达式
	 * @param binding
	 * @param expressions
	 * @param maxSuccessRunCount 最大成功执行次数
	 * @return
	 */
	private int executeExpression(final Binding binding, final Expression[] expressions, int maxSuccessRunCount) {
		// 循环执行脚本
		int successCount = 0;
		for (int i = 0; i < expressions.length; i++) {
			Expression exp = expressions[i];
			Script script = getScript(exp.where());
			script.setBinding(binding);
			Object evalRes = script.run();
			// 如果满足条件则执行返回脚本
			if (evalRes != null && evalRes.getClass().equals(Boolean.class) && (boolean) evalRes == true) {
				successCount++;
				// 注入返回对象变量
				Script resultScript = getScript(exp.then());
				resultScript.setBinding(binding);
				resultScript.run();
				if (maxSuccessRunCount > 0 && successCount >= maxSuccessRunCount) {
					break;
				}
			}
		}
		return successCount;
	}

	/**
	 * 设置绑定的方法参数
	 * 
	 * @param binding
	 * @param parameterNames
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void setBind(Binding binding, String[] parameterNames, Object[] args, Obj[] components, Obj result)
			throws InstantiationException, IllegalAccessException {

		// 参数
		for (int i = 0; i < parameterNames.length; i++) {
			String pName = parameterNames[i];
			binding.setVariable(pName, args[i]);
		}

		// spring容器变量
		if (components != null) {
			for (Obj obj : components) {
				binding.setVariable(obj.v(), getObject(obj.cls()));
			}
		}

		// 返回对象
		if (result != null && result.cls() != void.class) {
			binding.setVariable(result.v(), result.cls().newInstance());
		}

	}

	/**
	 * 获取脚本对象
	 * 
	 * @param scriptText
	 * @return
	 */
	private Script getScript(String scriptText) {
		Script script = scriptCache.get(scriptText);
		if (script == null) {
			try {
				Class<?> groovyClass = groovyClassLoader.parseClass(scriptText);
				GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
				script = (Script) groovyObject;
				scriptCache.put(scriptText, script);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return script;
	}

	/**
	 * 取spring的组件对象，缓存优先
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T getObject(Class<T> cls) {
		Object o = springContentCache.get(cls);
		if (o == null) {
			o = applicationContext.getBean(cls);
			springContentCache.put(cls, o);
		}
		return (T) o;
	}

}
