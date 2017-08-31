package com.fast.dev.component.webservice.impl.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.HttpSessionRequiredException;

import com.fast.dev.component.webservice.impl.util.HessianSecurityUtil;

/**
 * 带安全签名的方法实现
 * 
 * @author 练书锋
 *
 */
public class HessianServiceExporterSecurity extends HessianServiceExporter {

	private final Logger logger = LoggerFactory.getLogger(HessianServiceExporterSecurity.class);
	// 允许的误差时间
	private final static long offSetTime = 1000 * 60 * 60;

	// 安全令牌的获取方法,若无需校验则为null
	private WebServiceSecurityHandle webServiceSecurityHandle;

	public WebServiceSecurityHandle getSecurityHandle() {
		return webServiceSecurityHandle;
	}

	public void setSecurityHandle(WebServiceSecurityHandle webServiceSecurityHandle) {
		this.webServiceSecurityHandle = webServiceSecurityHandle;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (webServiceSecurityHandle != null) {
			// 取出应用令牌
			String aToken = request.getHeader("aToken");
			if (aToken == null) {
				throw throwException(request, "aToken不能为空");
			}
			// hash值
			String hash = request.getHeader("hash");
			if (hash == null) {
				throw throwException(request, "hash不能为空");
			}
			// 调用的客户机时间
			String time = request.getHeader("time");
			if (time == null) {
				throw throwException(request, "time不能为空");
			}
			// 取出安全令牌
			String sToken = webServiceSecurityHandle.getSecurityToken(aToken);
			if (sToken == null) {
				throw throwException(request, "sToken查询失败");
			}
			boolean validate = HessianSecurityUtil.validate(sToken, hash, time);
			if (!validate) {
				throw throwException(request, "hash校验失败");
			}
			if (Math.abs(Long.parseLong(time) - System.currentTimeMillis()) > offSetTime) {
				throw throwException(request, "systemTime错误");
			}
		}
		super.handleRequest(request, response);
	}

	// 抛出异常
	private ServletException throwException(HttpServletRequest request, String info) {
		logger.error("来访者：" + request.getRemoteHost() + " , 异常：" + info);
		return new HttpSessionRequiredException("来访者：" + request.getRemoteHost() + " , 异常：" + info);
	}

}
