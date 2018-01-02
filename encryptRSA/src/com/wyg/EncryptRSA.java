package com.wyg;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 非对称加密算法
 * @author JachinWei
 */
public class EncryptRSA{
	public static int decodeLen = 128;  
    public static int encodeLen = 100;
	public static String publicKeyString;
	public static String privateKeyString;

	//生成密匙对
	public static void generateKey() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		//生成钥匙对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		//保存字符串形式的钥匙
		EncryptRSA.publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		EncryptRSA.privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}
	
	// 公钥加密
	public static String encryptByRSA(String content) throws Exception {
		// 明文类型转换
		byte[] src = content.getBytes("UTF-8");
		// 根据公钥字符串产生公钥
		X509EncodedKeySpec seed = new X509EncodedKeySpec(Base64.getDecoder().decode(EncryptRSA.publicKeyString)); 
	    KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
	    PublicKey publicKey = keyFactory.generatePublic(seed);    
	    // 初始化加密器
	    Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 开始分组加密
		byte[] encode = new byte[] {};  
		for (int i = 0; i < src.length; i += encodeLen) {  
            byte[] subarray = ArrayUtils.subarray(src, i, i + encodeLen);  
            byte[] doFinal = cipher.doFinal(subarray);  
            encode = ArrayUtils.addAll(encode, doFinal);  
        }  
		// 类型转换
		return Base64.getEncoder().encodeToString(encode);
	}

	// 私钥解密
	public static String decryptByRSA(String content) throws Exception {
		// 密文类型转换
		byte[] src = Base64.getDecoder().decode(content);
		// 根据私钥字符串产生私钥
		byte[] keyBytes = Base64.getDecoder().decode(EncryptRSA.privateKeyString);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		// 初始化解密器
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		// 开始分组解密
		byte [] buffers = new byte[]{};  
        for (int i = 0; i < src.length; i += decodeLen) {  
            byte[] subarray = ArrayUtils.subarray(src, i, i + decodeLen);  
            byte[] doFinal = cipher.doFinal(subarray);  
            buffers = ArrayUtils.addAll(buffers, doFinal);  
        } 
        // 类型转换
     	return new String(buffers, "UTF-8");
	}
}