package com.wyg;

import javax.swing.SwingUtilities;

/**
 * 本项目为rsa加解密算法的应用
 * 主要功能有三：
 * 	1、自动生成密钥
 *  2、根据公钥进行加密
 *  3、根据私钥进行解密
 * 注意：1、加解密文件应该为UTF-8编码，否则会导致编码转换过程中的字符集置换混乱
 * @author JachinWei
 * @since 20180112 23:36
 */
public class Start {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainJFrame.getInstance().setVisible(true);
			}
		});
	}

}
