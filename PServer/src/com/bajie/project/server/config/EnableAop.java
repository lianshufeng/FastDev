package com.bajie.project.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 本配置允许使用spring 的AOP
 * @author Administrator
 *
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class EnableAop {

}
