package com.fast.dev.core.util.code;

import java.util.zip.CRC32;

/**
 * crc32 工具类
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年9月1日
 *
 */
public class Crc32Util {

	public static long hash(byte[] buff) {
		CRC32 crc32 = new CRC32();
		crc32.update(buff);
		return crc32.getValue();
	}

}
