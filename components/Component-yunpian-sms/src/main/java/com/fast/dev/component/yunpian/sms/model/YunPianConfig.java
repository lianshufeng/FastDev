package com.fast.dev.component.yunpian.sms.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

public class YunPianConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//云片APIKEY
	private String apikey;
    //编码格式。发送编码格式统一用UTF-8
    private  String encoding = "UTF-8";
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
