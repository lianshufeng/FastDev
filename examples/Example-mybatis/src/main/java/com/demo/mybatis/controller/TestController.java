package com.demo.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.mybatis.service.UserService;
import com.fast.dev.core.model.InvokerResult;

@Controller
public class TestController {

	/**
	 * 创建表
	 * 
	 * CREATE TABLE `User` ( `id` varchar(255) NOT NULL, `name` varchar(255) DEFAULT
	 * NULL, PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 * 
	 */

	@Autowired
	private UserService userService;

	/**
	 * http://127.0.0.1:8080/PServer/save.json?name=test
	 * 
	 * @return
	 */
	@RequestMapping("save.json")
	public InvokerResult<Object> save(String name) {
		return new InvokerResult<Object>(userService.save(name));
	}

	/**
	 * http://127.0.0.1:8080/PServer/count.json
	 * 
	 * @return
	 */
	@RequestMapping("count.json")
	public InvokerResult<Object> count() {
		return new InvokerResult<Object>(userService.count());
	}

	/**
	 * http://127.0.0.1:8080/PServer/get.json?name=test
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("get.json")
	public InvokerResult<Object> find(String name) {
		return new InvokerResult<Object>(userService.getUser(name));
	}

}
