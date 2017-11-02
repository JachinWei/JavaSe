package com.wyg.algorithm;
import java.util.Base64;

/**
 * Base64算法：简单的采用值与码替换的方式来加密，容易破解
 * 
 * @author JachinWei 
 * @date 20170721
 */
public class EncryptBase64 implements EncryptInterface{
	final public static String NAME = "Base64";
	/**
	 * 采用encode方法加密
	 * @return String
	 */
	@Override
	public String encrypt(String src) throws Exception{
		Base64.Encoder encoder = Base64.getMimeEncoder();
		return encoder.encodeToString(src.getBytes());
	}
	
	/**
	 * 采用decodeBuffer的方法解密,异常返回null，成功返回字符串
	 * @return String
	 */
	@Override
	public String decrypt(String src) throws Exception{
		Base64.Decoder decoder = Base64.getMimeDecoder();
		String returnMessage = null;
		returnMessage = new String(decoder.decode(src));
		return returnMessage;
	}
	
	@Override
	public String getName() {
		return EncryptBase64.NAME;
	}

	public static void main(String args[]) throws Exception{
		EncryptBase64 obj = new EncryptBase64();
		String str = "JachinWei1232";
		System.out.println(obj.encrypt(str));
		System.out.println(obj.decrypt(obj.encrypt(str)));
		
	}
}
