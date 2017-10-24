package com.fast.dev.component.yunpian.tell.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.component.yunpian.tell.model.YunPianConfig;
import com.fast.dev.component.yunpian.tell.service.YunPianTellService;
import com.fast.dev.component.yunpian.tell.utils.HttpHelper;

@Component
public class YunPianTellServiceImpl implements YunPianTellService{

	@Autowired
	public YunPianConfig yunPianConfig;
	
	@Override
	public String tplSendTell(long tpl_id, String tpl_value, String mobile) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", yunPianConfig.getApikey());
        params.put("callback_url", yunPianConfig.getCallback_url());
        params.put("mobile", mobile);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        
        String result =  HttpHelper.post("https://voice.yunpian.com/v2/voice/tpl_notify.json", params);
  
       
        
        //urlencode("name=深度学习&time=14:00","utf-8")
        
        System.out.println(result);
		return null;
	}

}
