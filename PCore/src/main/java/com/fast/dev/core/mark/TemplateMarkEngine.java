package com.fast.dev.core.mark;

import java.io.Writer;
import java.util.Map;

/**
 * 模版引擎接口,如果使用本方法，请把本方法的实现类配置到springBean中
 * 
 * @author 练书锋
 *
 */
public interface TemplateMarkEngine {

	/**
	 * 在输出管道中写入模版数据
	 * 
	 * @param outputStream
	 * @param templateName
	 * @param o
	 */
	public void writeStream(Writer writer, String templateName, Map<String, Object> o);

}
