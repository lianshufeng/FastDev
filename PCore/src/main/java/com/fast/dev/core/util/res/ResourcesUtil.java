package com.fast.dev.core.util.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StreamUtils;

import com.fast.dev.core.util.code.MD5Util;

/**
 * 解压资源工具
 * 
 * @author 练书锋
 *
 */
public class ResourcesUtil {

	private static Log log = LogFactory.getLog(ResourcesUtil.class);

	/**
	 * 加压资源
	 * 
	 * @param source @param target @throws Exception @throws
	 */
	public static void unpack(File jarFile, String resName, File target, UnpackType unpackType) {
		if (!jarFile.exists()) {
			return;
		}
		try {
			unpackFile(jarFile, resName, target, unpackType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("资源解压失败，服务端终止运行!");
		}
	}
	
	/**
	 * 加压资源
	 * 
	 * @param source @param target @throws Exception @throws
	 */
	public static void unpack(File jarFile, File folder , List<UnpackJarModel> unpackJarModels) {
		if (!jarFile.exists()) {
			return;
		}
		try {
			unpackFile(jarFile,folder, unpackJarModels);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("资源解压失败，服务端终止运行!");
		}
	}

	
	
	/**
	 * 解压文件
	 * 
	 * @param jarFile
	 * @param resName
	 * @param target
	 * @param unpackType
	 * @throws Exception
	 */
	private static void unpackFile(File jarFile , File folder , List<UnpackJarModel> unpackJarModels) throws Exception {
		FileInputStream inputStream = new FileInputStream(jarFile);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String zipName = zipEntry.getName();
			for (UnpackJarModel unpackJarModel : unpackJarModels) {
				// 判断目标文件不能为目录
				if (zipName.indexOf(unpackJarModel.getSource()) == 0 && !zipEntry.isDirectory()) {
					String preZipName;
					int flag = zipName.indexOf("/");
					if (flag == -1) {
						preZipName = zipName;
					} else {
						preZipName = zipName.substring(0, flag);
					}
					String extZipName = zipName.substring(preZipName.length(), zipName.length());
					// 目标文件是否存在
					File targetFile = new File(folder.getAbsolutePath()+unpackJarModel.getTarget() + "/" + extZipName);
					if (targetFile.exists()) {
						if (unpackJarModel.getUnpackType() == UnpackType.Override) {
							log.debug(jarFile.getName() + " -> " + zipName + " [override] ");
							write(zipInputStream, targetFile);
						} else {
							log.debug(jarFile.getName() + " -> " + zipName + " [skip]");
						}
					} else {
						log.debug(jarFile.getName() + " -> " + zipName + " [add]");
						write(zipInputStream, targetFile);
					}

				}
			}

		}
		zipInputStream.closeEntry();
		zipInputStream.close();
		inputStream.close();
	}
	
	/**
	 * 解压文件
	 * 
	 * @param jarFile
	 * @param resName
	 * @param target
	 * @param unpackType
	 * @throws Exception
	 */
	private static void unpackFile(File jarFile, String resName, File target, UnpackType unpackType) throws Exception {
		FileInputStream inputStream = new FileInputStream(jarFile);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		ZipEntry zipEntry;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String zipName = zipEntry.getName();
			// 判断目标文件不能为目录
			if (zipName.indexOf(resName) == 0 && !zipEntry.isDirectory()) {
				String preZipName;
				int flag = zipName.indexOf("/");
				if (flag == -1) {
					preZipName = zipName;
				} else {
					preZipName = zipName.substring(0, flag);
				}
				String extZipName = zipName.substring(preZipName.length(), zipName.length());
				// 目标文件是否存在
				File targetFile = new File(target + "/" + extZipName);
				if (targetFile.exists()) {
					if (unpackType == UnpackType.Override) {
						log.debug(jarFile.getName() + " -> " + zipName + " [override] ");
						write(zipInputStream, targetFile);
					} else {
						log.debug(jarFile.getName() + " -> " + zipName + " [skip]");
					}
				} else {
					log.debug(jarFile.getName() + " -> " + zipName + " [add]");
					write(zipInputStream, targetFile);
				}

			}

		}
		zipInputStream.closeEntry();
		zipInputStream.close();
		inputStream.close();
	}

	private static void write(InputStream inputStream, File targetFile) throws IOException {
		// 将数据拷贝到内存里
		byte[] bin = StreamUtils.copyToByteArray(inputStream);
//		 判断文件的重复性,必须存在的情况
		if (targetFile.exists()) {
			byte[] targetFileBin = FileUtils.readFileToByteArray(targetFile);
			String targetHash = new String(MD5Util.enCode(targetFileBin));
			String sourceHash = new String(MD5Util.enCode(bin));
			if (targetHash.equalsIgnoreCase(sourceHash)) {
				return;
			}
		}
		// 创建目录
		targetFile.getParentFile().mkdirs();
		// 文件写出
		FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
		// 解压资源
		StreamUtils.copy(bin, fileOutputStream);
		fileOutputStream.close();
	}


}
