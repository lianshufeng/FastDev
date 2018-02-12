package com.fast.dev.data.transfer;

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

	/**
	 * 通过参数类型传递具体数据
	 * 
	 * @return @throws
	 */
	public static DataOperate build(DataFileType dataFileType) {
		try {
			String className = DataTransferFactory.class.getPackage().getName() + ".impl." + dataFileType.toString()
					+ "DataOperate";
			Class<?> cls = Class.forName(className);
			return (DataOperate) cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
