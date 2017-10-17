package com.fast.dev.example.ali.pay.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.ali.pay.service.AliPayService;

@Controller
@RequestMapping(value = "/")
public class AliPayHtmlController {
	
	@Autowired
	private AliPayService aliPayService;
	
			
			
			//访问首页
			@RequestMapping("index.html")
			public void index(HttpServletResponse resp) throws IOException{

				resp.sendRedirect("/resources/index.html"); 
				
			}
			
			//访问返回结果
			@RequestMapping("return_url.html")
			public void return_url(HttpServletResponse resp,Map<String,String> params,String out_trade_no,String trade_no ) throws IOException{

				//放入处理业务代码
				
				System.out.println(out_trade_no);
				
				System.out.println(trade_no);
				
				//resp.sendRedirect("wappay/notify_url.jsp"); 
				
			}
			
			//访问通知
			@RequestMapping("notify_url.html")
			public void notify_url(HttpServletResponse resp,Map<String,String> params,String out_trade_no,String trade_no) throws IOException{

				//放入处理业务代码
				
				System.out.println(out_trade_no);
				
				System.out.println(trade_no);
				
				//resp.sendRedirect("/resources/notify_url.jsp"); 
				
			}
			
			
}
