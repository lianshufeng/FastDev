package com.fast.dev.component.ali.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fast.dev.component.ali.pay.model.AliPayConfig;
import com.fast.dev.component.ali.pay.model.AliPayOrder;
import com.fast.dev.component.ali.pay.service.AliPayService;

@Component
public class AliPayServiceImpl implements AliPayService{

	@Autowired
	public AliPayConfig aliPayConfig;
	
	//支付订单
	public String pay(AliPayOrder aliPayOrder){
		//初始化阿里支付
		AlipayClient alipayClient = ClientInit();
	    
		//封装订单信息
		AlipayTradeWapPayRequest alipay_request = InitPayModel(aliPayOrder);

		String form ="";
		
		try {
			return alipayClient.pageExecute(alipay_request).getBody();
			
		} catch (AlipayApiException e) {
			
			e.printStackTrace();
			
		}
		
		return form;
	}
	
	//1.初始化信息
	public AlipayClient ClientInit(){
		
		AlipayClient client = new DefaultAlipayClient(aliPayConfig.getURL(), aliPayConfig.getAPPID(),aliPayConfig.getRSA_PRIVATE_KEY(), aliPayConfig.getFORMAT(), aliPayConfig.getCHARSET(), aliPayConfig.getALIPAY_PUBLIC_KEY(),aliPayConfig.getSIGNTYPE());
		
		return client;
		
	}
	
	//2.封装订单信息
	public AlipayTradeWapPayRequest InitPayModel(AliPayOrder aliPayOrder){
		AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
		
		//封装请求支付信息
	    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
	    model.setOutTradeNo(aliPayOrder.getOut_trade_no());
	    model.setSubject(aliPayOrder.getSubject());
	    model.setTotalAmount(aliPayOrder.getTotal_amount());
	    model.setBody(aliPayOrder.getBody());
	    model.setTimeoutExpress(aliPayOrder.getTimeout_express());
	    model.setProductCode(aliPayOrder.getProduct_code());
	    alipay_request.setBizModel(model);
	    
	    //设置异步通知地址
	    alipay_request.setNotifyUrl(aliPayConfig.notify_url);
	    
	    // 设置同步地址
	    alipay_request.setReturnUrl(aliPayConfig.return_url);   
	    
	    return alipay_request;
	}

	@Override
	public AlipayTradeQueryResponse payQuery(String out_trade_no,String trade_no) {
		//返回参数
		AlipayTradeQueryResponse alipay_response =new AlipayTradeQueryResponse();
		//初始化阿里支付
		AlipayClient alipayClient = ClientInit();
		//获取返回参数	
		 AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
		 
		 AlipayTradeQueryModel model=new AlipayTradeQueryModel();
	     model.setOutTradeNo(out_trade_no);
	     model.setTradeNo(trade_no);
	     alipay_request.setBizModel(model);
		try {
			alipay_response = alipayClient.execute(alipay_request);
			
			return alipay_response;
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alipay_response;
	     
	}

	@Override
	public AlipayTradeRefundResponse refund(String out_trade_no,String trade_no,String refund_amount,String refund_reason,String out_request_no) {
		//初始化阿里支付
		AlipayClient alipayClient = ClientInit();
		//获取返回参数
		AlipayTradeRefundResponse alipay_response=new AlipayTradeRefundResponse();
		
		AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
		
		AlipayTradeRefundModel model=new AlipayTradeRefundModel();
		model.setOutTradeNo(out_trade_no);
		model.setTradeNo(trade_no);
		model.setRefundAmount(refund_amount);
		model.setRefundReason(refund_reason);
		model.setOutRequestNo(out_request_no);
		alipay_request.setBizModel(model);
		
		
		try {
			alipay_response = alipayClient.execute(alipay_request);
			
			System.out.println(alipay_response.getBody());
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return alipay_response;
	}

	@Override
	public AlipayTradeFastpayRefundQueryResponse refundQuery(String out_trade_no,String trade_no,String out_request_no) {
		//初始化阿里支付
		AlipayClient alipayClient = ClientInit();
		
		AlipayTradeFastpayRefundQueryRequest alipay_request = new AlipayTradeFastpayRefundQueryRequest();
		
		//返回参数
		AlipayTradeFastpayRefundQueryResponse alipay_response = new AlipayTradeFastpayRefundQueryResponse();
		
		AlipayTradeFastpayRefundQueryModel model=new AlipayTradeFastpayRefundQueryModel();
		model.setOutTradeNo(out_trade_no);
		model.setTradeNo(trade_no);
		model.setOutRequestNo(out_request_no);
		alipay_request.setBizModel(model);
		
		try {
			alipay_response = alipayClient.execute(alipay_request);
			
			System.out.println(alipay_response.getBody());
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return alipay_response;
		
	}

	
	
}
