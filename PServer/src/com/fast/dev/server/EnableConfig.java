package com.fast.dev.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 本配置允许使用spring 的AOP
 * 
 * @author Administrator
 *
 */
@Configuration
// 允许Aop
@EnableAspectJAutoProxy(proxyTargetClass = true)
// 允许Mvc
@EnableWebMvc
public class EnableConfig {

}
