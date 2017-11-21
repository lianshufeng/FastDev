package com.fast.dev.example.data.sign.controller;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.data.sign.constant.StringConstant;
import com.fast.dev.component.data.sign.util.DataSignUtil;
import com.fast.dev.core.model.InvokerResult;
import com.fast.dev.core.util.code.Crc32Util;
import com.fast.dev.core.util.code.JsonUtil;

@Controller
public class TestController {

	// 测试令牌
	public static final String TestToken = "28dc0adcf1dd417cb9476675d15c4584";

	// http://127.0.0.1:8080/PServer/test.json?_age=28&l=1234567890&z1=this is str
	// 中文汉汉字(!$()1 @（）！&_time=1512527400151&_hash=3394873984

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("test.json")
	public Object test(HttpServletRequest request) throws Exception {
		System.out.println(JsonUtil.toJson(request.getParameterMap()));
		return new InvokerResult<Object>("ok");
	}

	public static void main(String[] args) throws Exception {

		// 当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, 11, 6, 10, 30, 0);
		long time = (calendar.getTimeInMillis() /1000)*1000;

		// 提交数据
		Map<String, Object> data = new HashMap<String, Object>() {
			{
				put("l", 1234567890);
				put("z1", "各种特殊符号和@djpfjfd][]中文");
				put("_age", 28);
			}
		};
		// key排序
		String[] parameters = data.keySet().toArray(new String[data.size()]);
		Arrays.sort(parameters);

		String info = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (String key : parameters) {
			String val = String.valueOf(data.get(key));
			info += key + "=" + val + "&";
			outputStream.write(val.getBytes(StringConstant.DefaultCharset));
		}
		outputStream.flush();
		outputStream.close();
		byte[] datas = outputStream.toByteArray();
		System.out.println(new String(datas));

		
		datas = "names=G_NickName&names=G_Logo&names=G_DateOfBirth&names=G_City&names=height&names=G_Grade&names=G_Major&_uToken=0c7255dc1d8145e9bd5b70ea1cd8c314".getBytes();
		
		// 数据摘要
		long hash = DataSignUtil.sign(TestToken, time, datas);

//		String url = "http://127.0.0.1:8080/PServer/test.json?";
//		url += info;
//		url += "_time=" + time + "&_hash=" + hash;
//
//		System.out.println(url);
		
		System.out.println(hash);
		
	}
}
