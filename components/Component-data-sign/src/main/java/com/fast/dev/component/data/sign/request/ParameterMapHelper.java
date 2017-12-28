package com.fast.dev.component.data.sign.request;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * 转换为参数字典
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年12月19日
 *
 */
public class ParameterMapHelper {

	/**
	 * 转换到参数字典
	 * 
	 * @return
	 */
	public static Map<String, String[]> toParameterMap(byte[] buff, Charset charset, ParameterMapDecode dataDecode) {
		if (buff == null || charset == null) {
			return null;
		}
		return toParameterMap(new String(buff, charset), dataDecode);
	}

	/**
	 * 转换到参数字典
	 * 
	 * @return
	 */
	public static Map<String, String[]> toParameterMap(String content, ParameterMapDecode dataDecode) {
		if (StringUtils.isEmpty(content)) {
			return new HashMap<String, String[]>();
		}
		// 转换为参数
		Map<String, Set<String>> parameterMap = new HashMap<String, Set<String>>();
		for (String items : content.split("&")) {
			if (StringUtils.isEmpty(items)) {
				continue;
			}
			String[] kv = items.split("=");
			String key = null;
			Set<String> value = null;
			// 取出key，并实value装载对象
			if (kv.length > 0) {
				key = dataDecode.name(kv[0]);
				value = parameterMap.get(key);
				if (value == null) {
					value = new HashSet<String>();
					parameterMap.put(key, value);
				}
			}
			if (kv.length > 1) {
				value.add(dataDecode.value(kv[1]));
			}
		}
		// 转换为结果
		Map<String, String[]> result = new HashMap<String, String[]>();
		for (Entry<String, Set<String>> entry : parameterMap.entrySet()) {
			Set<String> sets = entry.getValue();
			String[] values = sets.toArray(new String[sets.size()]);
			result.put(entry.getKey(), values);
		}
		return result;
	}
}
