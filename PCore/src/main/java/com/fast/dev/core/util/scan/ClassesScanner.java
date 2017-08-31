package com.fast.dev.core.util.scan;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

/**
 * 基于Spring的类扫描工具
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年8月31日
 *
 */
public class ClassesScanner {

	/**
	 * 扫描指定的包
	 * 
	 * @param basePackage
	 * @return 返回类名集合
	 */
	public static List<String> scanComponents(final String basePackage) {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
		Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage.replaceAll("\\.", "/"));
		List<String> packageNames = new ArrayList<String>();
		for (BeanDefinition beanDefinition : beanDefinitions) {
			packageNames.add(beanDefinition.getBeanClassName());
		}
		return packageNames;
	}

}
