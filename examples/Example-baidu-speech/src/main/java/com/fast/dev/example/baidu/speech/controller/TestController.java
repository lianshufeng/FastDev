package com.fast.dev.example.baidu.speech.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.baidu.speech.service.SpeechService;

@Controller
@RequestMapping("speech")
public class TestController {

	@Autowired
	private SpeechService speechService;

	/**
	 * 语音直接转换为url并重定向
	 * 
	 * http://127.0.0.1:8080/PServer/speech/text?word=您有一个新任务
	 * 
	 * @param response
	 * @param cuid
	 * @param text
	 * @throws IOException
	 */
	@RequestMapping("text")
	public void text2Audio(HttpServletResponse response, String cuid, String word) throws IOException {
		String location = this.speechService.text2Audio(cuid, word);
		sendRedirect(response, location);
	}

	/**
	 * 通过模版调用语音接口
	 * 
	 * http://127.0.0.1:8080/PServer/speech/template
	 * 
	 * @param response
	 * @param cuid
	 * @throws IOException
	 */
	@RequestMapping("template")
	public void template2Audio(HttpServletResponse response, String cuid) throws IOException {
		String templateName = "newTaskTemplate.ftl";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("distance", RandomUtils.nextInt(100, 1000));
		params.put("time", RandomUtils.nextInt(0, 120));
		String location = this.speechService.template2Audio(cuid, templateName, params);
		sendRedirect(response, location);
	}

	private void sendRedirect(HttpServletResponse response, String location) throws IOException {
		response.sendRedirect(location);
	}

}
