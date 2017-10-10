package com.fast.dev.component.baidu.speech.model;

/**
 * 百度配置
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年10月7日
 *
 */
public class BaiduSpeechConfig {

	private String apiKey;
	private String secretKey;

	// 语速，取值0-9，默认为5中语速
	private Integer spd;
	// 音调，取值0-9，默认为5中语调
	private Integer pit;
	// 音量，取值0-15，默认为5中音量
	private Integer vol;
	// 发音人选择, 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
	private Integer per;

	public Integer getSpd() {
		return spd;
	}

	public void setSpd(Integer spd) {
		this.spd = spd;
	}

	public Integer getPit() {
		return pit;
	}

	public void setPit(Integer pit) {
		this.pit = pit;
	}

	public Integer getVol() {
		return vol;
	}

	public void setVol(Integer vol) {
		this.vol = vol;
	}

	public Integer getPer() {
		return per;
	}

	public void setPer(Integer per) {
		this.per = per;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
