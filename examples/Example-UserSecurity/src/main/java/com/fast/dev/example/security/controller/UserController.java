package com.fast.dev.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.user.security.service.UserSecurityHelper;
import com.fast.dev.core.model.InvokerResult;
import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.example.security.model.UserInfo;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserSecurityHelper userSecurityHelper;

	/**
	 * http://127.0.0.1:8080/PServer/user/login.json
	 * 
	 * @return
	 */
	@RequestMapping("login.json")
	public Object login() {
		return new InvokerResult<Object>(System.currentTimeMillis());
	}

	/**
	 * http://127.0.0.1:8080/PServer/user/info.json?_uToken=TestSession
	 * 
	 * @return
	 * @throws Exception 
	 */
	@Secured({ "ROLE_user" })
	@RequestMapping("info.json")
	public Object info() throws Exception {
		UserInfo info = userSecurityHelper.get(UserInfo.class).getUser();
		System.out.println(JsonUtil.toJson(info) +" hash : "+info.hashCode());
		return new InvokerResult<Object>(System.currentTimeMillis());
	}

	/**
	 * http://127.0.0.1:8080/PServer/user/logout.json
	 * 
	 * @return
	 */
	@RequestMapping("logout.json")
	public Object logout() {
		return new InvokerResult<Object>(System.currentTimeMillis());
	}

}
