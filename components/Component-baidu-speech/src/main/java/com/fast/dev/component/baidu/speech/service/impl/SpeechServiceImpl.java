package com.fast.dev.component.baidu.speech.service.impl;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.fast.dev.component.baidu.speech.model.BaiduSpeechConfig;
import com.fast.dev.component.baidu.speech.model.SpeechToken;
import com.fast.dev.component.baidu.speech.service.SpeechService;
import com.fast.dev.core.mark.TemplateMarkEngine;
import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.core.util.net.HttpClient;

/**
 * 百度通信接口实现
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月7日
 *
 */
public class SpeechServiceImpl implements SpeechService {
	// 百度访问令牌
	private BaiduSpeechConfig baiduSpeech = null;
	// 模版引擎
	private TemplateMarkEngine templateMarkEngine = null;
	// 语音令牌对象
	private SpeechToken speechToken = null;

	/**
	 * 设置百度的语音合成配置
	 * 
	 * @param baiduSpeech
	 */
	public void setBaiduSpeech(BaiduSpeechConfig baiduSpeech) {
		this.baiduSpeech = baiduSpeech;
	}

	/**
	 * 设置模版引擎
	 * 
	 * @param templateMarkEngine
	 */
	public void setTemplateMarkEngine(TemplateMarkEngine templateMarkEngine) {
		this.templateMarkEngine = templateMarkEngine;
	}

	@Override
	public String text2Audio(String cuid, String text) {
		try {
			return toParams(text, cuid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String template2Audio(String cuid, String templateName, Map<String, Object> params) {
		if (templateMarkEngine == null) {
			return null;
		}
		// 调用模版引擎获得文本内容
		StringWriter writer = new StringWriter();
		templateMarkEngine.writeStream(writer, templateName, params);
		String text = writer.getBuffer().toString();
		// 文本到语音
		return text2Audio(cuid, text);
	}

	/**
	 * 更新通信令牌
	 */
	private synchronized void updateSpeechToken() {
		if (this.speechToken != null && getSystemTime() < this.speechToken.getTimeoutStamp()) {
			return;
		}
		// 参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", this.baiduSpeech.getApiKey());
		params.put("client_secret", this.baiduSpeech.getSecretKey());
		String url = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&" + paramsToUri(params);
		try {
			this.speechToken = null;
			byte[] buff = new HttpClient().get(url);
			Map<?, ?> json = JsonUtil.toObject(new String(buff), Map.class);
			String accessToken = String.valueOf(json.get("access_token"));
			// 过期时间，毫秒
			long expiresIn = Long.parseLong(String.valueOf(json.get("expires_in"))) * 1000;
			// 1小时之前过期
			long beforeTime = 1000 * 60 * 60;
			this.speechToken = new SpeechToken(accessToken, getSystemTime() + expiresIn - beforeTime);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取令牌
	 * 
	 * @return
	 */
	private String getSpeechToken() {
		updateSpeechToken();
		return this.speechToken.getAccessToken();
	}

	private String toParams(String text, String cuid) throws UnsupportedEncodingException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lan", "zh");
		params.put("ctp", 1);
		params.put("cuid", cuid);
		params.put("tok", getSpeechToken());
		params.put("tex", URLEncoder.encode(text, "UTF-8"));

		setExtParams(params, "spd", this.baiduSpeech.getSpd());
		setExtParams(params, "pit", this.baiduSpeech.getPit());
		setExtParams(params, "vol", this.baiduSpeech.getVol());
		setExtParams(params, "per", this.baiduSpeech.getPer());
		
		
		return "http://tsn.baidu.com/text2audio?" + paramsToUri(params);
	}

	/**
	 * 设置扩展参数
	 * 
	 * @param params
	 * @param name
	 * @param value
	 */
	private static void setExtParams(final Map<String, Object> params, String name, Object value) {
		if (value != null) {
			params.put(name, value);
		}
	}

	/**
	 * 参数
	 * 
	 * @param params
	 * @return
	 */
	private String paramsToUri(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			sb.append(key + "=" + params.get(key) + "&");
		}
		return sb.toString();
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	private static long getSystemTime() {
		return System.currentTimeMillis();
	}

}
