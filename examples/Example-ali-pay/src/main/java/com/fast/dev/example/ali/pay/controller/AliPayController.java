package com.fast.dev.example.ali.pay.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fast.dev.component.ali.pay.model.AliPayConfig;
import com.fast.dev.component.ali.pay.model.AliPayOrder;
import com.fast.dev.component.ali.pay.model.BizContent;
import com.fast.dev.component.ali.pay.service.AliPayService;
import com.fast.dev.component.ali.pay.util.OrderInfoUtil;
import com.fast.dev.core.model.InvokerResult;

@Controller
@RequestMapping(value = "aliPay")
public class AliPayController {
	
	@Autowired
	private AliPayService aliPayService;
	
	@Autowired
	private AliPayConfig aliPayConfig;
	
			@RequestMapping("appPay.json")
			public InvokerResult<String> appPay(HttpServletResponse httpResponse,HttpServletResponse resp,String out_trade_no,String subject,String total_amount,String body,String timeout_express) throws AlipayApiException, UnsupportedEncodingException{
				//将参数填写进请求表单
				
				//内容
				body="测试支付";
				//订单号
				out_trade_no = "2017"+System.currentTimeMillis();
				//主题
				subject = "订单支付";
				//金额
				total_amount="0.01";
						
				AliPayOrder aliPayOrder = new AliPayOrder();
				
				aliPayOrder.setBody(body);
				
				aliPayOrder.setOut_trade_no(out_trade_no);
				
				aliPayOrder.setSubject(subject);
				
				aliPayOrder.setTotal_amount(total_amount);
				
				String orderInfo = aliPayService.appPay(aliPayOrder);
				
				return new InvokerResult<>(orderInfo);
			}
			
			
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
			
			
			/*
			 * 转账
			 */
			@RequestMapping("transfer.json")
			public InvokerResult<AlipayFundTransToaccountTransferResponse> transfer(String out_biz_no,String payee_account,String payer_show_name,String payee_real_name,String amount,String remark){
				
				AlipayFundTransToaccountTransferResponse response =aliPayService.transfer(out_biz_no, payee_account, payer_show_name, payee_real_name, amount, remark);
				
				return new InvokerResult<>(response);
			}
			
			
			
			
}
