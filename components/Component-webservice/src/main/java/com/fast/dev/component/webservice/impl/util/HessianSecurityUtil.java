package com.fast.dev.component.webservice.impl.util;

import com.fast.dev.core.util.code.MD5Util;

public class HessianSecurityUtil {

	/**
	 * 将数据hash
	 * 
	 * @param sToken
	 * @param content
	 * @return
	 */
	public static String hash(String sToken, String... contents) {
		String hash = null;
		if (contents != null && contents.length > 0) {
			for (int i = 0; i < contents.length; i++) {
				hash += MD5Util.md5(contents[i]);
			}
			hash = sToken + hash + hash;
			hash = MD5Util.md5(hash);
		}
		return hash;
	}

	/**
	 * 校验参数
	 * 
	 * @param sToken
	 * @param hash
	 * @param contents
	 * @return
	 */
	public static boolean validate(String sToken, String hash, String... contents) {
		String hashValue = hash(sToken, contents);
		if (hashValue == null) {
			return false;
		}
		return hashValue.equals(hash);
	}

}
