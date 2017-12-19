package com.fast.dev.component.data.sign.request;

/**
 * 参数字典解码
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年12月19日
 *
 */
public interface ParameterMapDecode {

	/**
	 * 解码参数名
	 * 
	 * @return
	 */
	public String name(String name);

	/**
	 * 解码数据
	 * 
	 * @return
	 */
	public String value(String value);
}
