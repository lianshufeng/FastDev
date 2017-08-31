package com.fast.dev.core.util.file;

/**
 * 路径工具，避免linux与windows的路径差异化
 * 
 * @作者 练书锋
 * @联系 251708339@qq.com
 * @时间 2016年12月16日
 *
 */
public class PathFormatUtil {

	/**
	 * 格式化为绝对路径，支持相对与绝对路径
	 * 
	 * @return
	 */
	public static boolean isFullPath(String path) {
		return path.indexOf(":") > -1 || path.substring(0, 1).equals("/");
	}

	/**
	 * 格式化uri
	 * 
	 * @param uri
	 * @return
	 */
	public static String formatUri(String uri) {
		String res = uri;
		// 格式化 反向
		while (res.indexOf("\\") > -1) {
			res = res.replaceAll("\\\\", "/");
		}
		while (res.indexOf("//") > -1) {
			res = res.replaceAll("//", "/");
		}
		return res;
	}

}
