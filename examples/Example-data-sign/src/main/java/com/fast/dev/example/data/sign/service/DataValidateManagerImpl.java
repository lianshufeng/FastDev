package com.fast.dev.example.data.sign.service;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.fast.dev.component.data.sign.model.ValidateSecretToken;
import com.fast.dev.component.data.sign.service.DataValidateManager;

@Component
public class DataValidateManagerImpl implements DataValidateManager {

	@Override
	public ValidateSecretToken secretToken(HttpServletRequest request) {
		// 2017-11-06 10:30:00
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 11, 6, 10, 30, 0);
		return new ValidateSecretToken("TestToken", calendar.getTimeInMillis());
	}

}
