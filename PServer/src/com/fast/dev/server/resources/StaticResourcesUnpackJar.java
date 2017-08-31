package com.fast.dev.server.resources;

import org.springframework.stereotype.Component;

import com.fast.dev.core.res.UnpackJarModel;
import com.fast.dev.core.res.UnpackType;

/**
 * Root目录解压到根目录里
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2017年8月31日
 *
 */
@Component
public class StaticResourcesUnpackJar extends UnpackJarModel {

	@Override
	public String getName() {
		return "Resources";
	}

	@Override
	public String getSource() {
		return "resources";
	}

	@Override
	public String getTarget() {
		return "/resources/";
	}

	@Override
	public UnpackType getUnpackType() {
		return UnpackType.Override;
	}

}
