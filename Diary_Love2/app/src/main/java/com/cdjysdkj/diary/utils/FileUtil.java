package com.cdjysdkj.diary.utils;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
*import static android.R.attr.author;

@className FileUtil
		* @author goubaihu
		* @function 对Bitmap转化成字节数组、
		* @createTime 2014年11月28号
		*/
public class FileUtil {
	/**
	 * 写入本地配置文件
	 *
	 * @param str
	 */
	public static boolean writeToFile(String str, String fileString) {
		File file = new File(fileString);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			if (!file.exists()) {
				File file2 = new File(file.getParent());
				file2.mkdir();
			}
			if (!file.isDirectory()) {
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			fos.write(str.getBytes());
			fos.close();
			fis = new FileInputStream(file);
			fis.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean writeToFile(String desFilePath, InputStream is)
			throws Exception {
		return writeToFile(new File(desFilePath), is);
	}

	/**
	 * 获取文件输入流
	 *
	 * @param filePath
	 *            文件路径
	 * @return 文件输入流
	 */
	public static InputStream getInputStream(String filePath) {
		if (!SDCardStatu.isSDCardAvailable()) {
			return null;
		}
		File gsyDataFile = new File(filePath);
		if (!gsyDataFile.exists()) {
			return null;
		}
		try {
			return new FileInputStream(gsyDataFile);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	/**
	 * 写入文件
	 *
	 * @param desFile
	 *            路径
	 * @param is
	 *            写入流
	 * @return 是否写入成功
	 * @throws Exception
	 */
	public static boolean writeToFile(File desFile, InputStream is)
			throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(desFile, false);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			return true;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param filePath
	 *            文件路径
	 * @return 判定存在
	 */
	public static boolean isExist(String filePath) {
		try {
			return new File(filePath).exists();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除文件
	 *
	 * @param path
	 *            文件路径
	 * @return 判定删除成功
	 */
	public static boolean deleteFileByPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return false;
		}
		File file = new File(path);
		return file.delete();
	}

	/**
	 * 删除目录下的文件
	 *
	 * @param directory
	 *            目录路径
	 * @return 判定删除成功
	 */
	public static boolean deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * @功能描述： <p>
	 *        将文件转换成String
	 *        </P>
	 * @param
	 * @return String
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public static String fileToString(String path) throws Exception {
		File file = new File(path);
		InputStream inStream = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		String strData = Base64.encodeToString(data, Base64.DEFAULT);
		// String strData = new String(data);
		outStream.close();
		inStream.close();
		return strData;

	}

	/**
	 *
	 * @功能描述： <p>
	 *        将string转换成file
	 *        </P>
	 * @param
	 * @return String data filePath 路径
	 *         StarConstant.PHONE_PATH+"recoder/amyy.amr"
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public static void stringToFile(String data, String filePath) {
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(data));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 *
	 * @param oldPath
	 *            String 原文件路径
	 * @param newPath
	 *            String 复制后路径
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1024];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();

		}

	}

}
