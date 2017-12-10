package com.fast.dev.component.ucloud.file.service.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fast.dev.component.ucloud.file.model.UcloudFileConfig;
import com.fast.dev.component.ucloud.file.service.FileService;

import cn.ucloud.ufile.UFileClient;
import cn.ucloud.ufile.UFileConfig;
import cn.ucloud.ufile.UFileRequest;
import cn.ucloud.ufile.UFileResponse;
import cn.ucloud.ufile.sender.GetSender;
import cn.ucloud.ufile.sender.PostSender;
import cn.ucloud.ufile.sender.PutSender;
@Component
public class FileServiceImpl implements FileService{

	@Autowired
	private UcloudFileConfig uCloudFileConfig;
	
	@Override
	public String uploadFile(String key,String filePath) {
		
		boolean isUpload=false;
		
		String bucketName = uCloudFileConfig.getBucketName();
		int i = 0;
		
		if(System.getProperty("os.name").toLowerCase().startsWith("win")) {
			i =1;
		}
		
		String configPath =this.getClass().getClassLoader().getResource("/").getPath().substring(i) +"config.properties";
		//System.out.println(configPath);
		
		UFileConfig.getInstance().loadConfig(configPath);
		
		UFileRequest request = new UFileRequest();
		request.setBucketName(bucketName);
		request.setKey(key);
		request.setFilePath(filePath);
		
		UFileClient ufileClient = null;
		System.out.println("PostFile BEGIN ...");
		try {
			ufileClient = new UFileClient();
			isUpload = postFile(ufileClient, request);
		} finally {
			ufileClient.shutdown();
		}
		
		if(isUpload){
			return "http://"+bucketName+".ufile.ucloud.com.cn/"+key;
		}
		
		return "";

		}
	
	
	public  boolean postFile(UFileClient ufileClient, UFileRequest request) {
		PostSender sender = new PostSender();
		sender.makeAuth(ufileClient, request);
		
		UFileResponse response = sender.send(ufileClient, request);
		if (response != null) {
			if(response.getStatusLine().getStatusCode() ==200){
				return true;
			}
		}
		return false;
	}

	@Override
	public String uploadFile(String key, FileInputStream fileInputStream) {
		boolean isUpload=false;
		
		String bucketName = uCloudFileConfig.getBucketName();
		int i = 0;
		
		if(System.getProperty("os.name").toLowerCase().startsWith("win")) {
			i =1;
		}
		
		String configPath =this.getClass().getClassLoader().getResource("/").getPath().substring(i) +"config.properties";
		
		UFileConfig.getInstance().loadConfig(configPath);
		
		UFileRequest request = new UFileRequest();
		request.setBucketName(bucketName);
		request.setKey(key);
		
		
		try {
			request.setInputStream(fileInputStream);
			request.setContentLength(fileInputStream.available());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UFileClient ufileClient = null;
		
		try {
			ufileClient = new UFileClient();
			isUpload = putFile(ufileClient, request);
		} finally {
			ufileClient.shutdown();
		}
		
		if(isUpload){
			 return "http://"+bucketName+".ufile.ucloud.com.cn/"+key;
		}
		
		return "";
	}
	
	
	private  boolean putFile(UFileClient ufileClient, UFileRequest request) {
		PutSender sender = new PutSender();
		sender.makeAuth(ufileClient, request);
		
		UFileResponse response = sender.send(ufileClient, request);
		if (response != null) {
			
			if(response.getStatusLine().getStatusCode()==200){
				return true;
			}
			
		}
		return false;
	}
	
}
