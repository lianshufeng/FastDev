package com.fast.dev.example.ucloud.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fast.dev.component.ucloud.file.service.FileService;
import com.fast.dev.core.model.InvokerResult;
@Controller
@RequestMapping(value = "file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@RequestMapping("fileUpload.json")
	private InvokerResult<String> fileUpload(String key){
		
		//必填
		String filePath ="c:/app28.png";
		
		fileService.uploadFile(key, filePath);
		
		return new InvokerResult<>("1");
		
	}
}
