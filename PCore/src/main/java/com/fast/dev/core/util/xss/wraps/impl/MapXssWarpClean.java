package com.fast.dev.core.util.xss.wraps.impl;

import java.util.Map;

import com.fast.dev.core.util.xss.XssUtil;
import com.fast.dev.core.util.xss.wraps.XssWarpClean;

@SuppressWarnings("unchecked")
public class MapXssWarpClean implements XssWarpClean<Map<Object, Object>> {

	@SuppressWarnings("rawtypes")
	@Override
	public Object warp(Map<Object, Object> map) {
		try {
			Map result = (Map) map.getClass().newInstance();
			for (Object key : map.keySet()) {
				result.put(XssUtil.wrapper(key), XssUtil.wrapper(map.get(key)));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
