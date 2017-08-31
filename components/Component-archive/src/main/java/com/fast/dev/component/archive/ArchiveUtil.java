package com.fast.dev.component.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.fast.dev.core.util.bytes.BytesUtil;
import com.fast.dev.core.util.file.PathFormatUtil;
import com.fast.dev.core.util.net.UrlUtil;
import com.fast.dev.core.util.stream.StreamUtils;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

/**
 * 存档文件 解压、压缩工具(支持rar与zip) ,解决中文乱码的问题
 * 
 * @作者 练书锋
 * @时间 2016年7月12日
 *
 */
@SuppressWarnings("deprecation")
public class ArchiveUtil {
	// 默认压缩文件的编码
	private static final String DefaultCharset = "GBK";
	private static final Charset InputCharset = Charset.forName(DefaultCharset);

	// 压缩文件头
	private static final byte[] RarHead = new byte[] { 82, 97, 114, 33 };
	private static final byte[] ZipHead = new byte[] { 80, 75, 3, 4 };

	/**
	 * 解压zip的压缩文件
	 * 
	 * @param inputStream
	 * @param targetDirectory
	 * @return
	 * @throws Exception
	 */
	public static List<String> unZip(final File inputFile, final String inputCharset, final File targetDirectory)
			throws Exception {
		if (inputFile == null || !inputFile.exists()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		// 创建或清空目录
		if (!targetDirectory.exists()) {
			targetDirectory.mkdirs();
		}
		FileInputStream inputStream = new FileInputStream(inputFile);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream, Charset.forName(inputCharset));
		ZipEntry zipEntry = null;
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			try {
				final File file = new File(targetDirectory.getAbsolutePath() + "/" + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					file.mkdirs();
				} else {
					File pFile = new File(file.getParent());
					if (!pFile.exists()) {
						pFile.mkdirs();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					StreamUtils.copy(zipInputStream, fileOutputStream);
					fileOutputStream.close();
					list.add(zipEntry.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			zipEntry.clone();
			zipInputStream.closeEntry();
		}
		inputStream.close();
		return list;
	}

	/**
	 * 解压rar的压缩文件
	 * 
	 * @param inputStream
	 * @param targetDirectory
	 * @return
	 * @throws IOException
	 * @throws RarException
	 */
	public static List<String> unRar(final File inputFile, final String inputCharset, final File targetDirectory)
			throws Exception {
		if (inputFile == null || !inputFile.exists()) {
			return null;
		}
		List<String> files = new ArrayList<String>();
		Archive archive = new Archive(inputFile);
		FileHeader fh = archive.nextFileHeader();
		while (fh != null) {
			// 获取名字的字节集
			byte[] fileNameBin = fh.getFileNameByteArray();
			String fileName = null;
			// 前部分名字为asc编码
			int nameSize = BytesUtil.find(fileNameBin, new byte[] { 00 });
			if (nameSize == -1) {
				fileName = new String(fileNameBin, inputCharset);
			} else {
				byte[] fileBin = new byte[nameSize];
				System.arraycopy(fileNameBin, 0, fileBin, 0, nameSize);
				fileName = new String(fileBin, inputCharset);
			}
			// 生成的目标路径
			File target = new File(UrlUtil.format(targetDirectory.getAbsolutePath() + "/" + fileName));
			if (!fh.isDirectory()) {
				files.add(fileName);
				// 原始数据流
				InputStream dataStream = archive.getInputStream(fh);
				// 写出文件
				FileUtils.copyInputStreamToFile(dataStream, target);
				// 关闭输入流
				dataStream.close();
			}
			// 下一个
			fh = archive.nextFileHeader();
		}
		// 关闭压缩工具
		archive.close();
		return files;
	}

	/**
	 * 解压文件，目前只支持 zip 与 rar
	 * 
	 * @param inputStream
	 * @param targetDirectory
	 * @return
	 * @throws Exception
	 */
	public static List<String> un(final File inputFile, final File targetDirectory) throws Exception {
		ArchiveType archiveType = detect(inputFile);
		if (archiveType == null) {
			return null;
		}
		if (archiveType.equals(ArchiveType.Rar)) {
			return unRar(inputFile, DefaultCharset, targetDirectory);
		} else if (archiveType.equals(ArchiveType.Zip)) {
			return unZip(inputFile, DefaultCharset, targetDirectory);
		}
		return null;
	}

	/**
	 * 探测归档文件类型
	 * 
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	public static ArchiveType detect(final File inputFile) throws Exception {
		if (inputFile == null || !inputFile.exists()) {
			return null;
		}
		// 读取文件头
		FileInputStream fileInputStream = new FileInputStream(inputFile);
		byte[] fileHead = new byte[4];
		fileInputStream.read(fileHead);
		fileInputStream.close();
		// 判断文件类型
		if (ArrayUtils.isEquals(fileHead, RarHead)) {
			return ArchiveType.Rar;
		} else if (ArrayUtils.isEquals(fileHead, ZipHead)) {
			return ArchiveType.Zip;
		} else {
			return ArchiveType.Unknown;
		}
	}

	/**
	 * 读取归档文件
	 * 
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	public static List<ArchiveFile> read(final File inputFile) throws Exception {
		ArchiveType archiveType = detect(inputFile);
		if (archiveType == null) {
			return null;
		}
		List<ArchiveFile> archiveFiles = new ArrayList<ArchiveFile>();
		if (archiveType.equals(ArchiveType.Rar)) {
			readRar(inputFile, archiveFiles);
		} else if (archiveType.equals(ArchiveType.Zip)) {
			readZip(inputFile, archiveFiles);
		} else {
			return null;
		}
		return archiveFiles;
	}

	/**
	 * 读取zip文件信息
	 * 
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	public static void readZip(final File inputFile, List<ArchiveFile> archiveFiles) throws Exception {
		if (inputFile == null || !inputFile.exists()) {
			return;
		}
		ZipFile zipFile = new ZipFile(inputFile.getAbsolutePath(), InputCharset);
		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();
			if (!zipEntry.isDirectory()) {

				ArchiveFile archiveFile = new ArchiveFile();
				archiveFile.setFileName(zipEntry.getName());
				archiveFile.setTime(zipEntry.getTime());
				archiveFile.setSize(zipEntry.getSize());
				archiveFile.setHash(crcToHex(zipEntry.getCrc()));
				archiveFiles.add(archiveFile);
			}
		}
		zipFile.close();
	}

	/**
	 * 读取rar文件信息
	 * 
	 * @param inputFile
	 * @return
	 * @throws Exception
	 */
	public static void readRar(final File inputFile, List<ArchiveFile> archiveFiles) throws Exception {
		if (inputFile == null || !inputFile.exists()) {
			return;
		}
		Archive archive = new Archive(inputFile);
		FileHeader fh = archive.nextFileHeader();
		while (fh != null) {
			if (!fh.isDirectory()) {
				// 获取名字的字节集
				byte[] fileNameBin = fh.getFileNameByteArray();
				String fileName = null;
				// 前部分名字为asc编码
				int nameSize = ArrayUtils.indexOf(fileNameBin, (byte) 0);
				if (nameSize == -1) {
					fileName = new String(fileNameBin, InputCharset);
				} else {
					byte[] fileBin = new byte[nameSize];
					System.arraycopy(fileNameBin, 0, fileBin, 0, nameSize);
					fileName = new String(fileBin, InputCharset);
				}
				ArchiveFile archiveFile = new ArchiveFile();
				archiveFile.setFileName(PathFormatUtil.formatUri(fileName));
				archiveFile.setSize(fh.getFullUnpackSize());
				archiveFile.setHash(crcToHex(fh.getFileCRC()));
				archiveFile.setTime(fh.getMTime().getTime());

				archiveFiles.add(archiveFile);
			}

			// 下一个
			fh = archive.nextFileHeader();
		}

		archive.close();
	}

	/**
	 * 长整型转到十六进制
	 * 
	 * @param l
	 * @param size
	 * @return
	 * @throws IOException
	 */
	private static String crcToHex(long crc) throws IOException {
		return BytesUtil.binToHex(BytesUtil.longToBin(crc, 4));
	}

	public enum ArchiveType {
		Zip, Rar, Unknown
	}

	public static class ArchiveFile {

		// 文件名
		private String fileName;

		// 文件hash
		private String hash;

		// 文件长度
		private long size;

		// 创建时间
		private long time;

		public ArchiveFile() {
			// TODO Auto-generated constructor stub
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public long getSize() {
			return size;
		}

		public void setSize(long size) {
			this.size = size;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

	}

}
