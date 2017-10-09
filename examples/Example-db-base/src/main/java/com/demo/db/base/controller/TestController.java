package com.demo.db.base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.db.base.service.UserService;
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

	/**
	 * http://127.0.0.1:8080/PServer/get.json?name=test
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("get.json")
	public InvokerResult<Object> get(String name) {
		return new InvokerResult<Object>(this.userService.get(name));
	}

	/**
	 * http://127.0.0.1:8080/PServer/remove.json?name=test
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("remove.json")
	public InvokerResult<Object> remove(String name) {
		return new InvokerResult<Object>(this.userService.remove(name));
	}

	/**
	 * http://127.0.0.1:8080/PServer/count.json
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("count.json")
	public InvokerResult<Object> count() {
		return new InvokerResult<Object>(this.userService.count());
	}

}
