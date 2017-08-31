package com.fast.dev.core.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * 载入配置文件
	 * 
	 * @param configName
	 * @return
	 * @throws IOException
	 */
	public static Properties loadProperties(String configName) {
		try {
			URL url = PropertiesUtil.class.getClassLoader().getResource(configName);
			FileInputStream in = new FileInputStream(new File(url.toURI()));
			Properties properties = new Properties();
			properties.load(in);
			in.close();
			return properties;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将配置文件载入到相应的bean对象里, 除非目标对象的key不匹配配置文件
	 * 
	 * @param configName
	 * @param o
	 */
	public static void loadToBean(String configName, Object o) {
		Class<?> cls = o.getClass();
		Properties properties = loadProperties(configName);
		for (Object key : properties.keySet()) {
			try {
				Field field = cls.getDeclaredField(String.valueOf(key));
				Object values = properties.get(key);
				if (field != null && values != null) {
					field.setAccessible(true);
					if (field.getType().isArray()) {
						String[] arrays = String.valueOf(values).split(",");
						field.set(o, arrays);
					} else {
						field.set(o, properties.get(key));
					}
				}

			} catch (Exception e) {
			}
		}
	}

}
