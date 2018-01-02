package com.wyg;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class RSAService {
	
	// 生成密钥文件
	public static String generateKey() {
		String msg = "";
		try {
			EncryptRSA.generateKey();
			File directory = new File(".");//设定为当前文件夹
			String publicPath = directory.getCanonicalPath() + File.separator + "publickey.txt";
			String privatePath = directory.getCanonicalPath() + File.separator + "privatekey.txt";
			FileOperation.write(publicPath, EncryptRSA.publicKeyString);
			FileOperation.write(privatePath, EncryptRSA.privateKeyString);
			msg = "公钥路径:" + publicPath + "\n私钥路径:" + privatePath;
		} catch (NoSuchAlgorithmException e) {
			msg = "生成密匙出错!";
		} catch (IOException e) {
			msg = "读写文件出错!";
		}
		return msg;
	}
	
	// 读取明文文件加密输出密文文件
	public static String encrypt(File publicKeyFile, File file) {
		String targetStr = null;
		String newFilePath = file.getParent() + File.separator +"encrypt_" + file.getName();
		String msg = "";
		try {
			targetStr = FileOperation.read(file.getAbsolutePath().toString());
			EncryptRSA.publicKeyString = FileOperation.read(publicKeyFile.getAbsolutePath().toString());
		} catch (IOException e) {
			msg = "文件读取错误!";
		}
		if (targetStr != null) {
			try {
				targetStr = EncryptRSA.encryptByRSA(targetStr);
			} catch (Exception e1) {
				msg = "加密程序出错!";
			}
			try {
				FileOperation.write(newFilePath, targetStr);
				msg = "加密完成!\n"+"加密文件路径:"+newFilePath;
			} catch (IOException e) {
				msg = "文件写入错误!";
			}
		}
		return msg;
	}
	
	// 读取密文件输出明文文件
	public static String decrypt(File privateKeyFile, File file) {
		String targetStr = null;
		String msg = "";
		String newFilePath = file.getParent() + File.separator + "decrypt_" + file.getName();
		try {
			targetStr = FileOperation.read(file.getPath());
			EncryptRSA.privateKeyString = FileOperation.read(privateKeyFile.getAbsolutePath().toString());
		} catch (IOException e) {
			msg = "文件读取错误!";
		}
		if (targetStr != null) {
			try {
				targetStr = EncryptRSA.decryptByRSA(targetStr);
			} catch (Exception e1) {
				msg = "解密程序出错!";
			}
			try {
				FileOperation.write(newFilePath, targetStr);
				msg = "解密完成!\n"+"解密文件路径:"+newFilePath;
			} catch (IOException e) {
				msg = "文件写入错误!";
			}
		}
		return msg;
	}
}
