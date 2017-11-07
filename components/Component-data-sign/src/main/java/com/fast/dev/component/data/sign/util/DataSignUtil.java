package com.fast.dev.component.data.sign.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.fast.dev.component.data.sign.constant.StringConstant;
import com.fast.dev.core.util.code.Crc32Util;

/**
 * 数据签名工具
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年11月6日
 *
 */
public class DataSignUtil {

	/**
	 * 数据签名
	 * 
	 * @param token
	 * @param time
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public static long sign(String token, long time, byte[]... datas) throws IOException {
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		// 令牌
		arrayOutputStream.write(token.getBytes(StringConstant.DefaultCharset));
		// 数据
		for (byte[] bin : datas) {
			arrayOutputStream.write(bin);
		}
		// 时间
		arrayOutputStream.write(String.valueOf(time).getBytes(StringConstant.DefaultCharset));
		arrayOutputStream.flush();
		byte[] buff = arrayOutputStream.toByteArray();
		arrayOutputStream.close();
		return Crc32Util.hash(buff);
	}

}
