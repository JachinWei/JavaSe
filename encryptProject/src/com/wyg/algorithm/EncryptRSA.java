package com.wyg.algorithm;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 非对称加密算法
 * 
 * @author JachinWei
 *
 */
public class EncryptRSA implements EncryptInterface {
	public final static String NAME = "RSA";
	public static String publicKeyString;
	public static String privateKeyString;

	public static void main(String[] args) throws Exception {
		String data = "Hello, world!I'm JachinWei.";
		EncryptRSA obj = new EncryptRSA();
		String encryStr = obj.encrypt(data);
		String decryStr = obj.decrypt(encryStr);
		System.out.println("解密后：" + encryStr);
		System.out.println("解密后：" + decryStr);
	}

	// 公钥加密
	private byte[] encryptByRSA(byte[] content, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(content);
	}

	// 私钥解密
	private byte[] decryptByRSA(byte[] content, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(content);
	}

	@Override
	public String encrypt(String src) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		//生成钥匙对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		//保存字符串形式的钥匙
		EncryptRSA.publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		EncryptRSA.privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
		//公匙加密
		byte[] encryptedBytes = this.encryptByRSA(src.getBytes("UTF-8"), publicKey);
		return Base64.getEncoder().encodeToString(encryptedBytes);  
	}

	@Override
	public String decrypt(String src) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(EncryptRSA.privateKeyString);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return new String(this.decryptByRSA(Base64.getDecoder().decode(src), privateKey), "UTF-8");
	}

	@Override
	public String getName() {
		return EncryptRSA.NAME;
	}

}