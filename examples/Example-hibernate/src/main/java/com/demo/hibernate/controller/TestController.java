package com.demo.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.hibernate.service.UserService;
import com.fast.dev.core.model.InvokerResult;

@Controller
public class TestController {

	@Autowired
	private UserService userService;

	/**
	 * 
	 * http://127.0.0.1:8080/PServer/save.json?name=test
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("save.json")
	public InvokerResult<Object> save(String name) {
		return new InvokerResult<Object>(this.userService.save(name));
	}

}
