package com.fast.dev.data.transfer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.TableItem;

/**
 * 数据导入接口
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public abstract class DataOperate {

	/**
	 * 读取文件流
	 * 
	 * @param fileInputStream
	 */
	public abstract Map<String, DataItem[]> read(InputStream inputStream);

	/**
	 * 写出数据流
	 * 
	 * @param outputStream
	 */
	public abstract boolean write(OutputStream outputStream, TableItem... tableItems);

}
