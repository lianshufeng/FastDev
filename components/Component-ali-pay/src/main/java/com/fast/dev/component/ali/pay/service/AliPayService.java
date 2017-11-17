package com.fast.dev.component.ali.pay.service;

import java.util.Map;

import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.fast.dev.component.ali.pay.model.AliPayOrder;

public interface AliPayService {

	/**
	 * 订单支付
	 * @param aliPayOrder
	 * @return
	 */
	public String pay(AliPayOrder aliPayOrder);
	
	/**
	 * APP订单支付
	 * @param aliPayOrder
	 * @return
	 */
	public String appPay(AliPayOrder aliPayOrder);
	/**
	 * 查询订单 
	 * @param out_trade_no  商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param trade_no  支付宝交易号
	 * @return
	 */
	public AlipayTradeQueryResponse payQuery(String out_trade_no,String trade_no);
	
	/**
	 * 退单
	 * @param out_trade_no
	 * @param trade_no
	 * @param refund_amount
	 * @param refund_reason
	 * @param out_request_no
	 * @return
	 */
	//out_trade_no:商户订单号
	//trade_no:支付宝交易号，和商户订单号二选一
	//refund_amount:退款金额，不能大于订单总金额
	//refund_reason:退款的原因说明
	//out_request_no:标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
	public AlipayTradeRefundResponse refund(String out_trade_no,String trade_no,String refund_amount,String refund_reason,String out_request_no);
	
	//退单查询
	//out_trade_no:商户订单号，和支付宝交易号二选一
	//trade_no:支付宝交易号，和商户订单号二选一
	//out_request_no:请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
	public AlipayTradeFastpayRefundQueryResponse refundQuery(String out_trade_no,String trade_no,String out_request_no);
	
	/**
	 * 
	 * @param out_biz_no
	    商户转账唯一订单号。发起转账来源方定义的转账单据ID，用于将转账回执通知给来源方。 
		不同来源方给出的ID可以重复，同一个来源方必须保证其ID的唯一性。 
		只支持半角英文、数字，及“-”、“_”。  【
		3142321423432】
		
	 * @param payee_account
	 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
	 【abc@sina.com】
	 
	 * @param payer_show_name
	 付款方姓名（最长支持100个英文/50个汉字）。显示在收款方的账单详情页。如果该字段不传，则默认显示付款方的支付宝认证姓名或单位名称。
	 【上海交通卡退款】
	 
	 * @param payee_real_name
	 收款方真实姓名（最长支持100个英文/50个汉字）。 
	 如果本参数不为空，则会校验该账户在支付宝登记的实名是否与收款方真实姓名一致。
	 【张三】
	 
	 * @param amount
	 转账金额，单位：元。 
	只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。 
	最大转账金额以实际签约的限额为准。
	【12.23】
	
	 * @param remark
	 转账备注（支持200个英文/100个汉字）。
	当付款方为企业账户，且转账金额达到（大于等于）50000元，remark不能为空。收款方可见，会展示在收款用户的收支详情中。
	【转账备注】
	 * @return
	 */
	public AlipayFundTransToaccountTransferResponse transfer(String out_biz_no,String payee_account,String payer_show_name,String payee_real_name,String amount,String remark);

	/**
	 * 验签接口
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	public boolean ValidationSign(Map<String, String> params,String sign);
}
