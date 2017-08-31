package com.fast.dev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置加载其他包
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年8月31日
 *
 */
@Configuration
@ComponentScan(basePackages = "org.example.template")
public class ImportDemo {

}
