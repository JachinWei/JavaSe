package com.wyg.algorithm;
/**
 * 加解密类的公共接口
 * @date 20170721
 * @author JachinWei
 *
 */
public interface EncryptInterface {
    String encrypt(String src) throws Exception;
    String decrypt(String src) throws Exception;
    String getName();
}
