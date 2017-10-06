package com.fast.dev.component.baidu.speech.service;

import java.util.Map;

public interface SpeechService {

	/**
	 * 文本转换为语音
	 * 
	 * @param text
	 * @return 返回请求的api
	 */
	public String text2Audio(String cuid, String text);

	/**
	 * 必须先配置模版引擎的依赖
	 * 
	 * @param cuid
	 * @param templateName
	 * @param o
	 * @return
	 */
	public String template2Audio(String cuid, String templateName, Map<String, Object> params);

}
