package com.fast.dev.component.yunpian.sms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.component.yunpian.sms.model.YunPianConfig;
import com.fast.dev.component.yunpian.sms.service.YunPianSmsService;
import com.fast.dev.component.yunpian.sms.utils.HttpHelper;

@Component
public class YunPianSmsServiceImpl implements YunPianSmsService {

	@Autowired
	private YunPianConfig yunPianConfig;

	@Override
	public String tplSendSms(long tpl_id, String tpl_value, String mobile) {
		
		Map < String, String > params = new HashMap < String, String > ();
		params.put("apikey", yunPianConfig.getApikey());
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        
       String responseText = HttpHelper.post("https://sms.yunpian.com/v2/sms/tpl_single_send.json", params);
       
		return responseText;
	}
	
	
	

}
