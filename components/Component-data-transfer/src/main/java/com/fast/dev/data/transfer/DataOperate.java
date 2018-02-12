package com.fast.dev.data.transfer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fast.dev.data.transfer.model.DataItem;
import com.fast.dev.data.transfer.model.FileOption;

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
	public abstract List<DataItem> read(InputStream inputStream);

	/**
	 * 写出数据流
	 * 
	 * @param outputStream
	 */
	public boolean write(OutputStream outputStream, List<DataItem> datas) {
		return write(outputStream, datas, null);
	}

	/**
	 * 写出数据流
	 * 
	 * @param outputStream
	 * @param datas
	 * @param option
	 *            导出相关信息的配置
	 * @return
	 */
	public abstract boolean write(OutputStream outputStream, List<DataItem> datas, FileOption option);

}
