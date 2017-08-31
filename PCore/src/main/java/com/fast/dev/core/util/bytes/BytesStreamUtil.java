package com.fast.dev.core.util.bytes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 管道工具
 * 
 * @作者: 练书锋
 * @联系: oneday@vip.qq.com
 * @时间: 2013年11月26日
 */
public class BytesStreamUtil {

	/**
	 * 写出管道在数据里
	 * 
	 * @param outputStream
	 * @param bin
	 * @throws IOException
	 */
	public final static int write(final OutputStream outputStream, int maxLength, final byte[]... bin)
			throws IOException {
		int count = 0;
		int limit = Integer.MAX_VALUE;
		if (maxLength < 4) {
			limit = (int) Math.pow(256, maxLength);
		}
		for (int i = 0; i < bin.length; i++) {
			outputStream.write(BytesUtil.intToBin(bin[i].length, maxLength));
			count += maxLength;
			if (bin[i].length > limit) {
				outputStream.write(BytesUtil.subBytes(bin[i], 0, limit));
				count += limit;
			} else {
				outputStream.write(bin[i]);
				count += bin[i].length;
			}
		}
		return count;
	}

	/**
	 * 读取字节集，与写的规则类似
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public final static byte[] read(final InputStream inputStream, final int maxLength) throws IOException {
		byte[] sizeBin = new byte[maxLength];
		inputStream.read(sizeBin);
		int dataSize = BytesUtil.binToInt(sizeBin);
		byte[] dataBin = new byte[dataSize];
		inputStream.read(dataBin);
		return dataBin;
	}

}
