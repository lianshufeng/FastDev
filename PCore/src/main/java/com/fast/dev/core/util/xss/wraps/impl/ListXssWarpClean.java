package com.fast.dev.core.util.xss.wraps.impl;

import java.util.List;

import com.fast.dev.core.util.xss.XssUtil;
import com.fast.dev.core.util.xss.wraps.XssWarpClean;

@SuppressWarnings({ "unchecked" })
public class ListXssWarpClean implements XssWarpClean<List<Object>> {

	@Override
	public Object warp(List<Object> os) {
		try {
			List<Object> result = os.getClass().newInstance();
			for (int i = 0; i < os.size(); i++) {
				Object object = os.get(i);
				result.add(XssUtil.wrapper(object));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
