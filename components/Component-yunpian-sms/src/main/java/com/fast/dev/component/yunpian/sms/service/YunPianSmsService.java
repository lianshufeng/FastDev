package com.fast.dev.component.yunpian.sms.service;

import org.springframework.stereotype.Service;

@Service 
public interface YunPianSmsService {

	//发送短消息
	public String tplSendSms( long tpl_id, String tpl_value,String mobile);
	
}
