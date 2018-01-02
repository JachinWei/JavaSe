package com.wyg;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * 单例设计主界面
 * @author JachinWei
 */
public class MainJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static MainJFrame singleObj;
	private JButton generateKeyBtn = new JButton("按此生成密钥文件");
	private JButton encryptBtn = new JButton("加密");
	private JButton decryptBtn = new JButton("解密");
	private JTextArea msgJTA = new JTextArea();
	private JScrollPane msgJSP = new JScrollPane(msgJTA);
	private JPanel jp = new JPanel();

	// 线程安全的静态获取单例对象的方法
	public static MainJFrame getInstance() {
		if (MainJFrame.singleObj == null) {
			synchronized (MainJFrame.class) {
				if (MainJFrame.singleObj == null) {
					MainJFrame.singleObj = new MainJFrame();
				}
			}
		}
		return MainJFrame.singleObj;
	}
	
	//私有构造方法
	private MainJFrame() {
		this.setSize(400, 300);
		this.setTitle("信息安全概论之RSA加解密算法");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置窗口垂直居中显示
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 500) / 2, (height - 400) / 2);

		this.setResizable(false);
		this.setJPanel();
		this.setContentPane(this.jp);
	}
	
	// 设置面板
	private void setJPanel() {
		this.jp.setBounds(0, 0, 400, 300);

		this.jp.setLayout(null);
		this.generateKeyBtn.setBounds(110, 20, 160, 30);
		this.encryptBtn.setBounds(150, 60, 80, 30);
		this.decryptBtn.setBounds(150, 100, 80, 30);
		this.msgJSP.setBounds(45, 160, 300, 100);
		
		this.msgJTA.setLineWrap(true); // 激活自动换行功能
		this.msgJTA.setWrapStyleWord(true); // 激活断行不断字功能

		this.generateKeyBtn.addMouseListener(new AlgorithmInnerAction());
		this.encryptBtn.addMouseListener(new AlgorithmInnerAction());
		this.decryptBtn.addMouseListener(new AlgorithmInnerAction());

		this.jp.add(this.generateKeyBtn);
		this.jp.add(this.encryptBtn);
		this.jp.add(this.msgJSP);
		this.jp.add(this.decryptBtn);
	}
	
	// 算法类按钮的鼠标事件类
	private class AlgorithmInnerAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!((JButton) e.getSource()).isEnabled()) {
				return;
			}
			if (e.getSource() == MainJFrame.this.generateKeyBtn) {
				MainJFrame.this.generateKeyBtn.setEnabled(false);
				MainJFrame.this.msgJTA.setText(RSAService.generateKey());
				MainJFrame.this.generateKeyBtn.setEnabled(true);
			} else if (e.getSource() == MainJFrame.this.encryptBtn) {
				JOptionPane.showMessageDialog(MainJFrame.this.jp, "请选择公钥密匙文件", "提示", JOptionPane.WARNING_MESSAGE);  
				// 获取公钥字符串
				File publicKeyFile = MainJFrame.this.getFile();
				JOptionPane.showMessageDialog(MainJFrame.this.jp, "请选择要加密的文件", "提示", JOptionPane.WARNING_MESSAGE);
				File encryptFile = publicKeyFile == null ? null : MainJFrame.this.getFile();
				if(encryptFile != null) {
					MainJFrame.this.msgJTA.setText(RSAService.encrypt(publicKeyFile, encryptFile));
				}
			} else if (e.getSource() == MainJFrame.this.decryptBtn) {
				JOptionPane.showMessageDialog(MainJFrame.this.jp, "请选择私钥密匙文件", "提示", JOptionPane.WARNING_MESSAGE);  
				// 获取公钥字符串
				File privateKeyFile = MainJFrame.this.getFile();
				JOptionPane.showMessageDialog(MainJFrame.this.jp, "请选择要解密的文件", "提示", JOptionPane.WARNING_MESSAGE);
				File decryptFile = privateKeyFile == null ? null : MainJFrame.this.getFile();
				if(decryptFile != null) {
					MainJFrame.this.msgJTA.setText(RSAService.decrypt(privateKeyFile, decryptFile));
				}
			}
		}
	}

	// 打开资源管理器获取文件信息的方法
	private File getFile() {
		File file = new File(".");
		JFileChooser jfc = new JFileChooser(file);
		jfc.setFileSelectionMode(0);// 设定只能选择到文件
		int state = jfc.showOpenDialog(this);// 此句是打开文件选择器界面的触发语句
		if (state == 1) {
			return null;// 撤销则返回
		} else {
			return file = jfc.getSelectedFile();// f为选择到的文件
		}
	}

	public static void main(String[] args) {
		// 事件调度线程中执行创建窗口的方法
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainJFrame.getInstance().setVisible(true);
			}
		});
	}

}
