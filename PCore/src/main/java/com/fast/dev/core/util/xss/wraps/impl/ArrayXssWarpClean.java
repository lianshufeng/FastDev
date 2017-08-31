package com.fast.dev.core.util.xss.wraps.impl;

import com.fast.dev.core.util.xss.XssUtil;
import com.fast.dev.core.util.xss.wraps.XssWarpClean;

public class ArrayXssWarpClean implements XssWarpClean<Object[]> {

	@Override
	public Object warp(Object[] os) {
		for (int i = 0; i < os.length; i++) {
			os[i] = XssUtil.wrapper(os[i]);
		}
		return os;
	}

}
