package com.wyg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 加解密文件操作工具类
 * @author JachinWei
 */
public class FileOperation {

	// 读取文件中的字符串
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

	// 创建新的文件存储字符串
	public static void write(String filePath, String content) throws IOException {
		File file = new File(filePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();
	}
}
