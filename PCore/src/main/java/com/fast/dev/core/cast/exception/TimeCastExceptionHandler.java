package com.fast.dev.core.cast.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.cast.container.TimeCastThreadContainer;
import com.fast.dev.core.cast.model.TimeCastModel;
import com.fast.dev.core.cast.service.TimeCastService;
import com.fast.dev.core.model.ExceptionModel;
import com.fast.dev.core.model.ExceptionResult;

/**
 * 处理有异常时间开销情况
 * 
 * @author 练书锋
 *
 */
@Component
public class TimeCastExceptionHandler implements HandlerExceptionResolver {

	@Autowired
	private TimeCastThreadContainer timeCastThreadContainer;

	@Autowired
	private TimeCastService timeCastService;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		TimeCastModel timeCastModel = timeCastThreadContainer.get();
		if (timeCastModel != null) {
			ex.printStackTrace();
			ExceptionResult<Object> invokerResult = new ExceptionResult<Object>();
			invokerResult.setException(new ExceptionModel(ex.getMessage(), ex.getClass().getName()));
			timeCastService.setCastTime(invokerResult);
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("invokerResult", invokerResult);
			return modelAndView;
		}
		return null;
	}

}
