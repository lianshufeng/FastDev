package com.fast.dev.core.util.xss.wraps.impl;

import java.lang.reflect.Field;

import com.fast.dev.core.util.xss.XssUtil;
import com.fast.dev.core.util.xss.wraps.XssWarpClean;

public class ObjectXssWarpClean implements XssWarpClean<Object> {

	@Override
	public Object warp(Object o) {
		try {
			Field[] fields = o.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (!f.isAccessible()) {
					f.setAccessible(true);
				}
				Object val = f.get(o);
				f.set(o, XssUtil.wrapper(val));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return o;
	}
}
