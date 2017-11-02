package com.wyg.algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 加解密文件操作工具类
 * 
 * @date 20170722
 * @author JachinWei
 */
public class FileOperation {
	/**
	 * 读取文件中的字符串
	 * 
	 * @param String
	 * @return String
	 * @throws IOException
	 */
	public static String read(String filePath) throws IOException {
		int size = 1024;
		StringBuffer strBuf = new StringBuffer("");
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		char[] cbuf = new char[size];
		int readChar = 0;
		while ((readChar = br.read(cbuf)) != -1) {
			if (readChar < size) {
				strBuf.append(new String(cbuf).substring(0, readChar));
				break;
			}
			strBuf.append(cbuf);
		}
		br.close();
		return strBuf.toString();
	}

	/**
	 * 创建新的文件存储字符串
	 * 
	 * @param String
	 * @param String
	 * @throws IOException
	 */
	public static void write(String filePath, String content) throws IOException {
		File file = new File(filePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();
	}

	/**
	 * 读取明文文件加密输出密文文件
	 * 
	 * @param File
	 * @param EncryptAndDencryptInterface
	 * @return String
	 */
	public static String encrypt(File file, EncryptInterface aInterface) {
		String targetStr = null;
		String parentPath = file.getParent();
		String fileName = file.getName();
		@SuppressWarnings("static-access")
		String fileSeparator = file.separator;
		String newFilePath = parentPath + fileSeparator + aInterface.getName() + "_encrypt_" + fileName;
		try {
			targetStr = FileOperation.read(file.getAbsolutePath().toString());
		} catch (IOException e) {
			return "文件读取错误!";
		}
		if (targetStr != null) {
			try {
				targetStr = aInterface.encrypt(targetStr);
			} catch (Exception e1) {
				return "加密程序出错!";
			}
			try {
				FileOperation.write(newFilePath, targetStr);
				return  "文件加密成功输出为" + aInterface.getName() + "_encrypt_" + fileName;
			} catch (IOException e) {
				return "文件写入错误!";
			}
		}
		return "error";
	}

	/**
	 * 读取密文件输出明文文件
	 * 
	 * @param File
	 * @param EncryptAndDencryptInterface
	 * @return String
	 */
	public static String decrypt(File file, EncryptInterface aInterface) {
		String targetStr = null;
		String parentPath = file.getParent();
		String fileName = file.getName();
		@SuppressWarnings("static-access")
		String fileSeparator = file.separator;
		String fielPath = file.getPath();
		String newFilePath = parentPath + fileSeparator + aInterface.getName() + "_dencrypt_" + fileName;
		try {
			targetStr = FileOperation.read(fielPath);
		} catch (IOException e) {
			return "文件读取错误!";
		}
		if (targetStr != null) {
			try {
				targetStr = aInterface.decrypt(targetStr);
			} catch (Exception e1) {
				return "解密程序出错!";
			}
			try {
				FileOperation.write(newFilePath, targetStr);
				return "文件解密成功输出为" + aInterface.getName() + "_dencrypt_" + fileName;
			} catch (IOException e) {
				return "文件写入错误!";
			}
		}
		return "Error!";
	}

	public static void main(String[] args){
		File file1 = new File("E:\\待保存\\1.txt");
		EncryptAES.encodeRule = "wyg";
		String encryStr = FileOperation.encrypt(file1, new EncryptAES());
		File file2 = new File("E:\\待保存\\AES_encrypt_1.txt");
		String decryStr = FileOperation.decrypt(file2, new EncryptAES());
		System.out.println(encryStr);
		System.out.println(decryStr);
	}

}
