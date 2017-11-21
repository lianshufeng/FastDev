package com.fast.dev.component.ali.pay.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 蒋健
 *
 */
@Component
public class AliPayConfig implements Serializable{

		private static final long serialVersionUID = 1L;
		// 商户appid
		public  String APPID;
		// 私钥 pkcs8格式的
		public  String RSA_PRIVATE_KEY ;
		// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public  String notify_url;
		// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
		public  String return_url ;
		// 请求网关地址	
		public  String URL = "https://openapi.alipay.com/gateway.do";
		// 编码
		public  String CHARSET = "UTF-8";
		// 返回格式
		public  String FORMAT = "json";
		// 支付宝公钥
		public  String ALIPAY_PUBLIC_KEY ="";
		// 日志记录目录
		public  String log_path = "/log";
		// RSA2
		public  String SIGNTYPE = "RSA2";
		
		public String getAPPID() {
			return APPID;
		}
		public void setAPPID(String aPPID) {
			APPID = aPPID;
		}
		public String getRSA_PRIVATE_KEY() {
			return RSA_PRIVATE_KEY;
		}
		public void setRSA_PRIVATE_KEY(String rSA_PRIVATE_KEY) {
			RSA_PRIVATE_KEY = rSA_PRIVATE_KEY;
		}
		public String getNotify_url() {
			return notify_url;
		}
		public void setNotify_url(String notify_url) {
			this.notify_url = notify_url;
		}
		public String getReturn_url() {
			return return_url;
		}
		public void setReturn_url(String return_url) {
			this.return_url = return_url;
		}
		public String getURL() {
			return URL;
		}
		public void setURL(String uRL) {
			URL = uRL;
		}
		public String getCHARSET() {
			return CHARSET;
		}
		public void setCHARSET(String cHARSET) {
			CHARSET = cHARSET;
		}
		public String getFORMAT() {
			return FORMAT;
		}
		public void setFORMAT(String fORMAT) {
			FORMAT = fORMAT;
		}
		public String getLog_path() {
			return log_path;
		}
		public void setLog_path(String log_path) {
			this.log_path = log_path;
		}
		public String getSIGNTYPE() {
			return SIGNTYPE;
		}
		public void setSIGNTYPE(String sIGNTYPE) {
			SIGNTYPE = sIGNTYPE;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getALIPAY_PUBLIC_KEY() {
			return ALIPAY_PUBLIC_KEY;
		}
		public void setALIPAY_PUBLIC_KEY(String aLIPAY_PUBLIC_KEY) {
			ALIPAY_PUBLIC_KEY = aLIPAY_PUBLIC_KEY;
		}
	
		
	
}
