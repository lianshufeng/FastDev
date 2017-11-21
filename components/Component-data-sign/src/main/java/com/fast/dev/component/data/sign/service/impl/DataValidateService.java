package com.fast.dev.component.data.sign.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fast.dev.component.data.sign.constant.StringConstant;
import com.fast.dev.component.data.sign.model.DataSignConfig;
import com.fast.dev.component.data.sign.model.ValidateSecretToken;
import com.fast.dev.component.data.sign.service.DataValidateManager;
import com.fast.dev.component.data.sign.type.DataValidateResult;
import com.fast.dev.component.data.sign.util.DataSignUtil;
import com.fast.dev.core.model.ExceptionModel;
import com.fast.dev.core.model.ExceptionResult;
import com.fast.dev.core.util.ResponseUtil;

@Component
public class DataValidateService {

	@Resource
	private DataSignConfig dataSignConfig;

	@Resource
	private DataValidateManager dataValidateManager;

	@Resource
	private ApplicationContext applicationContext;

	/**
	 * 校验请求
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public boolean validateData(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String timeName = dataSignConfig.getParameter().getRequestTimeName();
		String hashName = dataSignConfig.getParameter().getRequestHashName();

		// 时间
		String timeValue = request.getParameter(timeName);
		// 数据摘要
		String hashValue = request.getParameter(hashName);
		// 校验必要参数
		if (StringUtils.isEmpty(timeValue)) {
			sendExcption(request, response, DataValidateResult.TimeEmpty);
			return false;
		}
		if (StringUtils.isEmpty(hashValue)) {
			sendExcption(request, response, DataValidateResult.HashEmpty);
			return false;
		}

		// 转换参数类型
		long timeVal = Long.valueOf(timeValue);
		long hashVal = Long.valueOf(hashValue);

		// 获取数据加密令牌
		ValidateSecretToken validateSecretToken = this.dataValidateManager.secretToken(request);

		// 时间校验
		if (!validateTime(timeVal, validateSecretToken)) {
			sendExcption(request, response, DataValidateResult.TimeOut);
			return false;
		}

		// 请求的参数
		Map<String, String[]> parameterMap = new HashMap<String, String[]>(request.getParameterMap());
		parameterMap.remove(hashName);
		parameterMap.remove(timeName);
		// 排除过滤的参数
		for (String name : this.dataSignConfig.getParameter().getExcludeRequestName()) {
			parameterMap.remove(name);
		}

		// 校验数据
		if (!validateData(timeVal, hashVal, validateSecretToken, parameterMap)) {
			sendExcption(request, response, DataValidateResult.HashError);
			return false;
		}

		return true;
	}

	/**
	 * 校验数据
	 * 
	 * @param hashValue
	 * @param validateSecretToken
	 * @return
	 * @throws IOException
	 */
	private boolean validateData(long timeValue, long hashValue, ValidateSecretToken validateSecretToken,
			Map<String, String[]> parameterMap) throws IOException {

		// key排序
		String[] keys = parameterMap.keySet().toArray(new String[parameterMap.size()]);
		Arrays.sort(keys);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		// 遍历每一个参数,并排序多个数组
		for (String key : keys) {
			String[] values = parameterMap.get(key);
			if (values != null) {
				Arrays.sort(values);
				for (String value : values) {
					arrayOutputStream.write(value.getBytes(StringConstant.DefaultCharset));
				}
			}
		}
		arrayOutputStream.flush();
		byte[] buff = arrayOutputStream.toByteArray();
		arrayOutputStream.close();

		// 进行数据签名
		long dataHash = DataSignUtil.sign(validateSecretToken.getSecretToken(), timeValue, buff);
		return dataHash == hashValue;
	}

	/**
	 * 校验时间，是否超时
	 * 
	 * @param timeValue
	 * @param validateSecretToken
	 * @return
	 */
	private boolean validateTime(long timeValue, ValidateSecretToken validateSecretToken) {
		long result = timeValue - validateSecretToken.getTimeStamp();
		return Math.abs(result) < dataSignConfig.getValidate().getTimeOut();
	}

	/**
	 * 发送异常
	 * 
	 * @throws Exception
	 */
	private void sendExcption(HttpServletRequest request, HttpServletResponse response,
			DataValidateResult dataValidateResult) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		ExceptionResult<Object> exceptionResult = new ExceptionResult<Object>();
		exceptionResult.setException(new ExceptionModel("error", dataValidateResult.name()));
		result.put("invokerResult", exceptionResult);
		response.addHeader("Access-Control-Allow-Origin", "*");
		ResponseUtil.write(request, response, result);
	}

}
