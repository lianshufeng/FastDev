package com.fast.dev.idea.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fast.dev.core.util.bytes.BytesUtil;
import com.fast.dev.core.util.code.JsonUtil;
import com.fast.dev.idea.util.Md5RsaUtil;

@Controller
public class IdeaController {

	// 数据签名头
	private final static String DataHash = "<!-- ${hash} -->";
	// 数据签名内容
	private final static String DataContent = "<ObtainTicketResponse><message></message><prolongationPeriod>607875500</prolongationPeriod><responseCode>OK</responseCode><salt>${salt}</salt><ticketId>1</ticketId><ticketProperties>licensee=${userName}	licenseType=0	</ticketProperties></ObtainTicketResponse>";

	@RequestMapping("rpc/obtainTicket.action")
	public ModelAndView request(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 请求的参数
		Map<String, String[]> parameterMap = request.getParameterMap();
		// 内容
		final String content = getContent(DataContent, parameterMap);
		// 计算替换hash
		String hash = getContent(DataHash, new HashMap<String, String[]>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				put("hash", new String[] { BytesUtil.binToHex(Md5RsaUtil.Sign(content.getBytes())) });
			}
		});
		// 返回模版
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("content", hash + "\n" + content);
		modelAndView.setViewName("obtainTicket");
		// 打印下请求
		System.out.println(request.getRemoteHost() + " : " + JsonUtil.toJson(parameterMap));
		return modelAndView;
	}

	@RequestMapping("rpc/releaseTicket.action")
	public ModelAndView releaseTicket() {
		return new ModelAndView("releaseTicket");
	}

	/**
	 * 模版替换
	 * 
	 * @param source
	 * @param parameterMap
	 * @return
	 */
	private static String getContent(final String source, final Map<String, String[]> parameterMap) {
		String content = source;
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String[] values = entry.getValue();
			if (values != null && values.length > 0) {
				content = content.replaceAll("\\$\\{" + entry.getKey() + "\\}", values[0]);
			}
		}
		return content;
	}

}
