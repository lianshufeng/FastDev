package com.fast.dev.example.yunpian.tell.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class TplHelper {
	
	
  public static String ConvertTpl(Map<String, String>map) throws UnsupportedEncodingException{
	  
	  String tpl_value="";
	  
	  int i =0;
	  
	  for (String key : map.keySet()) {
		  //在此处添加Key和Value 参数
		  tpl_value =tpl_value + key+"="+ URLEncoder.encode(map.get(key), "UTF-8") + "&";
		  if(i ==map.size()-1){
			  //结束处不需要&
			  tpl_value = tpl_value +key+"="+ URLEncoder.encode(map.get(key), "UTF-8");
		  }
		  i = i + 1;
	 }
		
	  
	return tpl_value;
	  
	  
  }
  /**
   * 将数字转换文字
   * @return
   */
 public static String ConvertNumToChar(String num){
	 
	 int length = num.length();
	 
	 String returnChar = "";
	 
	 for(int i=1;i<=length;i++){
		 
		 returnChar = returnChar +getCharByNum(Integer.parseInt(num.substring(i-1,i)));
		 
	 }
	 
	 return returnChar;
 }
  
 public static String getCharByNum(int num){
	 
	  char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	 
	 
	 return String.valueOf(numArray[num]);
 }
 
}
