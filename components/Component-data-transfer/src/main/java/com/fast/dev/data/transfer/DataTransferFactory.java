package com.fast.dev.data.transfer;

import java.util.HashMap;
import java.util.Map;

import com.fast.dev.data.transfer.impl.ExcelDataOperate;
import com.fast.dev.data.transfer.type.DataFileType;

/**
 * 数据导入工厂
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2018年2月12日
 *
 */
public abstract class DataTransferFactory {

	// 缓存数据转换类型
	private static final Map<DataFileType, Class<? extends DataOperate>> DataFileClasses = new HashMap<DataFileType, Class<? extends DataOperate>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(DataFileType.Excel, ExcelDataOperate.class);
		}
	};

	/**
	 * 通过参数类型传递具体数据
	 * 
	 * @return @throws
	 */
	public static DataOperate build(DataFileType dataFileType) {
		try {
			Class<? extends DataOperate> cls = DataFileClasses.get(dataFileType);
			if (cls != null) {
				return cls.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
