package com.fast.dev.component.ali.pay.service;

import java.util.Map;

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
	
	
	//支付后返回URL接口
}
