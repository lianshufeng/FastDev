package com.fast.dev.component.ucloud.file.service;

import java.io.FileInputStream;

public interface FileService {

	//上传文件
	public String uploadFile(String key,String filePath);
	
	//上传文件
	public String uploadFile(String key,FileInputStream fileInputStream);
	
}
