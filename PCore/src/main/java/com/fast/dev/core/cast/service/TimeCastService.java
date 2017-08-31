package com.fast.dev.core.cast.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.cast.container.TimeCastThreadContainer;
import com.fast.dev.core.cast.model.TimeCastModel;
import com.fast.dev.core.model.InvokerResult;

/**
 * 时间开销计算的工具类
 * 
 * @author 练书锋
 *
 */
@Component
public class TimeCastService {

	@Autowired
	private TimeCastThreadContainer timeCastThreadContainer;

	/**
	 * 设置开销时间
	 * 
	 * @param modelAndView
	 */
	public void setCastTime(ModelAndView modelAndView) {
		if (modelAndView != null) {
			Map<String, Object> model = modelAndView.getModel();
			if (model != null) {
				Object o = model.get("invokerResult");
				if (o != null && o instanceof InvokerResult<?>) {
					InvokerResult<?> invokerResult = (InvokerResult<?>) o;
					setCastTime(invokerResult);
				}
			}
		}

	}

	/**
	 * 设置开销时间
	 * 
	 * @param invokerResult
	 */
	public void setCastTime(InvokerResult<?> invokerResult) {
		TimeCastModel timeCastModel = timeCastThreadContainer.get();
		long accessTime = timeCastModel.getAccessTime();
		invokerResult.setUsedTime(System.currentTimeMillis() - accessTime);
	}

}
