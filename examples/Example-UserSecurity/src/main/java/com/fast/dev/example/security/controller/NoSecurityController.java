package com.fast.dev.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.core.model.InvokerResult;

@Controller
public class NoSecurityController {

	/**
	 * http://127.0.0.1:8080/PServer/test.json
	 * @return
	 */
	@RequestMapping("test.json")
	public Object test() {
		return new InvokerResult<Object>(System.currentTimeMillis());
	}

}
