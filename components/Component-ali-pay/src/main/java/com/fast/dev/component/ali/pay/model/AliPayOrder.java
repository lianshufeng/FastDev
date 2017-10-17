package com.fast.dev.component.ali.pay.model;


/**
 *  支付宝支付接口订单实体
 * @author 蒋健
 *
 */

public class AliPayOrder {
	
	
	
		//商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复    
		//[示例值:20150320010101001]
	    private String out_trade_no;
	    
	    //订单标题 
	    //[示例值:Iphone6 16G]
	    private String subject;
	    
	    //订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000] 
	    //如果同时传入【可打折金额】和【不可打折金额】，该参数可以不用传入； 
	    //如果同时传入了【可打折金额】，【不可打折金额】，【订单总金额】三者，则必须满足如下条件：【订单总金额】=【可打折金额】+【不可打折金额】
	    //[示例值:88.88]
	    private String total_amount;
	    
	    //订单描述 
	    //[示例值:Iphone6 16G]
	    private String body;
	    
	    //该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
	    //[示例值:90m]
	    private String timeout_express = "30m";
	    
	    //销售产品码 
	    //[示例值:FACE_TO_FACE_PAYMENT]
	    private String product_code;

		public String getOut_trade_no() {
			return out_trade_no;
		}

		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getTotal_amount() {
			return total_amount;
		}

		public void setTotal_amount(String total_amount) {
			this.total_amount = total_amount;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getTimeout_express() {
			return timeout_express;
		}

		public void setTimeout_express(String timeout_express) {
			this.timeout_express = timeout_express;
		}

		public String getProduct_code() {
			return product_code;
		}

		public void setProduct_code(String product_code) {
			this.product_code = product_code;
		}
	    
	    
}
