package com.wyg.algorithm;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 对称加密算法
 * 
 * @author JachinWei
 *
 */
public class EncryptAES implements EncryptInterface {
	final public static String NAME = "AES";
	public static String encodeRule = "wyg";

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		EncryptAES de1 = new EncryptAES();
		EncryptAES de2 = new EncryptAES();
		String msg = "这是测试案例：-？?wyg201441404417";
		String encontent = null;
		String decontent = null;
		try {
			encontent = de1.encrypt(msg);
			decontent = de2.decrypt(encontent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("明文是:" + msg);
		System.out.println("加密后:" + encontent);
		System.out.println("解密后:" + decontent);
	}

	@Override
	public String encrypt(String src) throws Exception {
		// 初始化密钥生成器
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, new SecureRandom(EncryptAES.encodeRule.getBytes()));
		// 获得密匙
		SecretKey deskey = keygen.generateKey();
		// 加码器
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] resultByte = c.doFinal(src.getBytes("UTF-8"));
		// base64加密转换
		return Base64.getEncoder().encodeToString(resultByte);
	}

	@Override
	public String decrypt(String src) throws Exception {
		// 初始化密钥生成器
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128, new SecureRandom(EncryptAES.encodeRule.getBytes()));
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

	@Override
	public String getName() {
		return EncryptAES.NAME;
	}
}
