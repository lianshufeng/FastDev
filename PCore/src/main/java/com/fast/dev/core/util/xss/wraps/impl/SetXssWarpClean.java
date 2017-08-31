package com.fast.dev.core.util.xss.wraps.impl;

import java.util.Set;

import com.fast.dev.core.util.xss.XssUtil;
import com.fast.dev.core.util.xss.wraps.XssWarpClean;

@SuppressWarnings("unchecked")
public class SetXssWarpClean implements XssWarpClean<Set<Object>> {

	@Override
	public Object warp(Set<Object> set) {
		try {
			Set<Object> result = set.getClass().newInstance();
			for (Object object : set) {
				result.add(XssUtil.wrapper(object));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
