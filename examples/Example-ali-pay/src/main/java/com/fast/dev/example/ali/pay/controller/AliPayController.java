package com.fast.dev.example.ali.pay.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fast.dev.component.ali.pay.model.AliPayOrder;
import com.fast.dev.component.ali.pay.service.AliPayService;
import com.fast.dev.core.model.InvokerResult;

@Controller
@RequestMapping(value = "aliPay")
public class AliPayController {
	
	@Autowired
	private AliPayService aliPayService;
	
			@RequestMapping("pay.json")
			public InvokerResult<String> pay(HttpServletResponse httpResponse,HttpServletResponse resp,String out_trade_no,String subject,String total_amount,String body,String timeout_express,String product_code) throws IOException{

				//将参数填写进请求表单
				AliPayOrder aliPayOrder = new AliPayOrder();
				
				aliPayOrder.setBody(body);
				
				aliPayOrder.setOut_trade_no(out_trade_no);
				
				aliPayOrder.setProduct_code(product_code);
				
				aliPayOrder.setSubject(subject);
				
				aliPayOrder.setTotal_amount(total_amount);
				
				//获取到返回的URL
				String  formHtml = aliPayService.pay(aliPayOrder);
				
				httpResponse.setContentType("text/html;charset=UTF-8");
				
				//返回静态页面添加到页面中
				return new InvokerResult<>(formHtml);
			}
			
			@RequestMapping("payQuery.json")
			public InvokerResult<AlipayTradeQueryResponse> payQuery(HttpServletResponse httpResponse,HttpServletResponse resp,String out_trade_no,String trade_no) throws IOException{

				//获取到返回的URL
				AlipayTradeQueryResponse  request = aliPayService.payQuery(out_trade_no, trade_no);
				
				//返回静态页面添加到页面中
				return new InvokerResult<>(request);
			}
			
}
