package com.fast.dev.core.util.log;

import com.fast.dev.core.util.code.JsonUtil;

public class LogUtil {

	/**
	 * 打印对象
	 * 
	 * @param o
	 */
	public static void print(Object o) {
		try {
			System.out.println(JsonUtil.toJson(o));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
