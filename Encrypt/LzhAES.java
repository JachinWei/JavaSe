package com.lzh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 对称加密算法
 * 
 * @author 刘泽慧
 *
 */
public class AES {

	// 测试例子
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception{
		AES aes = new AES();
		
		//输入需加密文本文件的路径
		String filePath = "E:\\Plaintext.txt";
		String key = "201441404423";
		File file = new File(filePath);
		
		//开始加密并输出加密文件
		String encryptContent = aes.encrypt(aes.read(filePath), key);
		System.out.println("加密完成!");
		String newFilePath = file.getParent() + file.separator + "encrypt_" + file.getName();
		aes.write(newFilePath, encryptContent);
		
		//开始解密并输出解密文件
		String decryptContent = aes.decrypt(encryptContent, key);
		System.out.println("解密完成!");
		newFilePath = file.getParent() + file.separator + "dencrypt_" + file.getName();
		aes.write(newFilePath, decryptContent);
		
	}
	
	
	public String read(String filePath) throws IOException {
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
	
	//写文件
	public void write(String filePath, String content) throws IOException {
		File file = new File(filePath);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(content);
		bw.close();
	}
	
	//加密
	public String encrypt(String src, String encodeRule) throws Exception {
		// 初始化密钥生成器
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, new SecureRandom(encodeRule.getBytes()));
		// 获得密匙
		SecretKey deskey = keygen.generateKey();
		// 加码器
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] resultByte = c.doFinal(src.getBytes("UTF-8"));
		// base64加密转换
		return Base64.getEncoder().encodeToString(resultByte);
	}

	// 解密
	public String decrypt(String src, String encodeRule) throws Exception {
		// 初始化密钥生成器
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, new SecureRandom(encodeRule.getBytes()));
		// 获得密匙
		SecretKey deskey = keygen.generateKey();
		//密文转换处理
		byte[] tranByte = Base64.getDecoder().decode(src);
		// 加码器
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, deskey);
		byte[] resultByte = c.doFinal(tranByte);
		return new String(resultByte, "UTF-8");
	}
}
