package com.fast.dev.example.yunpian.sms.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.yunpian.sms.service.YunPianSmsService;
import com.fast.dev.core.model.InvokerResult;
import com.fast.dev.example.yunpian.sms.util.TplHelper;

@Controller
@RequestMapping("YunPianSms")
public class YunPianSmsController {

	@Autowired
	public YunPianSmsService yunPianSmsService;
	
	//模板发送短信
	@RequestMapping("tplSendSms.json")
	public InvokerResult<String> sendSmsByTpl(long tpl_id,String mobile,String tpl_value){
		
		 tpl_id = 1952988L;
		 
		 mobile = "15826110609";
		 
		 Map<String, String> map = new HashMap();
			
		map.put("code", "123884");
		
		try {
			
			tpl_value = TplHelper.ConvertTpl(map);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new InvokerResult<String>(yunPianSmsService.tplSendSms(tpl_id, tpl_value, mobile));
	}
	
}
