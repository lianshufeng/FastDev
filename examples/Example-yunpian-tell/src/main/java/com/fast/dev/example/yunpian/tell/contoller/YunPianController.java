package com.fast.dev.example.yunpian.tell.contoller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.yunpian.tell.service.YunPianTellService;
import com.fast.dev.core.model.InvokerResult;
import com.fast.dev.example.yunpian.tell.util.TplHelper;

@Controller
@RequestMapping(value = "YunPian")
public class YunPianController {
	
	@Autowired
	public YunPianTellService yunPianTellService;
	
	/**
	 * 发送语音消息
	 * @param httpResponse
	 * @param resp
	 * @param tpl_id 模板ID
	 * @param tpl_value 模板内容
	 * @param mobile 发送的手机号码
	 * @throws IOException
	 */
	
			@RequestMapping("tplSendTell.json")
			public void tplSendTell(HttpServletResponse httpResponse,HttpServletResponse resp,String tpl_id,String tpl_value,String mobile) throws IOException{

				mobile = "18523098272";
				
				//构造需要的填充信息
				
				Map<String, String> map = new HashMap();
				
				map.put("name", "庄长虹");
				
				map.put("begindate", "14:00");
				
				map.put("orderid", TplHelper.ConvertNumToChar("3392801761"));
				
				tpl_value = TplHelper.ConvertTpl(map);
				
				yunPianTellService.tplSendTell(1992732, tpl_value, mobile);
				
			}
			
			
			
}
