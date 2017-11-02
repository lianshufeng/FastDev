package com.fast.dev.component.yunpian.tell.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

@Service  
public interface YunPianTellService {

	
	/**
	 * 
	 * @param tpl_id  审核通过的模版ID
	 * @param tpl_value  模板： 课程#name#在#time#开始。
		最终发送结果： 课程深度学习在14:00开始。 tplvalue=urlencode("name=深度学习&time=14:00","utf-8");
	 * @param mobile 被发送号码
	 * @param callback_url 回调URL  本条语音验证码状态报告推送地址
	 * @return
			count	Integer	成功发送的语音呼叫次数
			fee	Integer	扣费金额为0，语音通知后扣费
			sid	String	记录id，32位的唯一字符串
	 * @throws IOException
	 */
	 public  String tplSendTell(long tpl_id, String tpl_value, String mobile) throws IOException;
	
}
