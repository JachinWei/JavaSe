package com.wyg.start;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.wyg.algorithm.EncryptAES;
import com.wyg.algorithm.EncryptBase64;
import com.wyg.algorithm.EncryptRSA;
import com.wyg.algorithm.FileOperation;

/**
 * 单例设计主界面
 * 
 * @author JachinWei
 *
 */
public class MainJFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static MainJFrame singleObj;
	private JButton base64Btn = new JButton("Base64");
	private JButton aesBtn = new JButton("AES");
	private JButton rsaBtn = new JButton("RSA");
	private JButton encryptBtn = new JButton("加密");
	private JButton decryptBtn = new JButton("解密");
	private JTextArea encryptJTA = new JTextArea();
	private JTextArea decryptJTA = new JTextArea();
	private JScrollPane encryptJS = new JScrollPane(encryptJTA);
	private JScrollPane decryptJS = new JScrollPane(decryptJTA);
	private AlgorithmChoices flag;

	private enum AlgorithmChoices {
		BASE64, AES, RSA;
	}

	// 算法类按钮的鼠标事件类
	private class AlgorithmInnerAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!((JButton) e.getSource()).isEnabled()) {
				return;
			}
			this.init();
			if (e.getSource() == base64Btn) {
				flag = AlgorithmChoices.BASE64;
				encryptJTA.setText("");
				decryptJTA.setText("");
				base64Btn.setEnabled(false);
			} else if (e.getSource() == aesBtn) {
				flag = AlgorithmChoices.AES;
				encryptJTA.setText("请在这里输入加密密匙后才点加密！");
				decryptJTA.setText("在这里输入对应的加密密匙才能正确解密！");
				aesBtn.setEnabled(false);
			} else if (e.getSource() == rsaBtn) {
				flag = AlgorithmChoices.RSA;
				encryptJTA.setText("");
				decryptJTA.setText("在这里输入私匙才能解密！");
				rsaBtn.setEnabled(false);
			}
		}

		private void init() {
			base64Btn.setEnabled(true);
			aesBtn.setEnabled(true);
			rsaBtn.setEnabled(true);
		}
	}

	private class EncryptionInnerAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			File file = this.getFile();
			 String newLine = System.getProperty("line.separator");  
			if (e.getSource() == encryptBtn) {
				if (file != null) {
					switch (flag) {
					case BASE64:
						encryptJTA.setText(file.getName() + FileOperation.encrypt(file, new EncryptBase64()));
						break;
					case AES:
						if (encryptJTA.getText() != null) {
							EncryptAES objAES1 = new EncryptAES();
							EncryptAES.encodeRule = encryptJTA.getText().trim();
							encryptJTA.setText(file.getName() + FileOperation.encrypt(file, objAES1));
							encryptJTA.setText(encryptJTA.getText() + newLine+"密匙：" + EncryptAES.encodeRule);
						} else {
							encryptJTA.setText("注意哦！这里不能为空！");
						}
						break;
					case RSA:
						EncryptRSA objRSA1 = new EncryptRSA();
						encryptJTA.setText(file.getName() + FileOperation.encrypt(file, objRSA1));
						encryptJTA.setText(encryptJTA.getText() + newLine+"私匙：" + EncryptRSA.privateKeyString);
						break;
					default:
					}
				}
			} else if (e.getSource() == decryptBtn) {
				if (file != null) {
					switch (flag) {
					case BASE64:
						decryptJTA.setText(file.getName() + FileOperation.decrypt(file, new EncryptBase64()));
						break;
					case AES:
						if (decryptJTA.getText() != null) {
							EncryptAES objAES2 = new EncryptAES();
							EncryptAES.encodeRule = decryptJTA.getText().trim();
							decryptJTA.setText(file.getName() + FileOperation.decrypt(file, objAES2));
						} else {
							decryptJTA.setText("注意哦！这里不能为空！");
						}
						break;
					case RSA:
						if (decryptJTA.getText() != null) {
							EncryptRSA objRSA2 = new EncryptRSA();
							EncryptRSA.privateKeyString = decryptJTA.getText().trim();
							decryptJTA.setText(file.getName() + FileOperation.decrypt(file, objRSA2));
						} else {
							decryptJTA.setText("注意哦！这里不能为空！");
						}
						break;
					default:
					}
				}
			}
		}

		/**
		 * 打开资源管理器获取文件信息的方法
		 * 
		 * @return File
		 */
		private File getFile() {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(0);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return null;// 撤销则返回
			} else {
				File file = jfc.getSelectedFile();// f为选择到的文件
				return file;
			}
		}
	}

	// 设置左面板：存放按钮
	private JPanel setLeftJPanel() {
		JPanel leftJP = new JPanel();
		leftJP.setBounds(0, 0, 100, 400);

		leftJP.setLayout(null);
		this.base64Btn.setBounds(10, 50, 80, 30);
		this.aesBtn.setBounds(10, 150, 80, 30);
		this.rsaBtn.setBounds(10, 250, 80, 30);

		this.base64Btn.addMouseListener(new AlgorithmInnerAction());
		this.aesBtn.addMouseListener(new AlgorithmInnerAction());
		this.rsaBtn.addMouseListener(new AlgorithmInnerAction());

		leftJP.add(this.base64Btn);
		leftJP.add(this.aesBtn);
		leftJP.add(this.rsaBtn);
		return leftJP;

	}

	// 设置右面板
	private JPanel setRightJPanel() {
		JPanel rightJP = new JPanel();
		rightJP.setBounds(100, 0, 400, 400);
		rightJP.setLayout(null);

		this.encryptBtn.setBounds(150, 15, 80, 30);
		this.decryptBtn.setBounds(150, 180, 80, 30);
		this.encryptJS.setBounds(20, 60, 340, 105);
		this.decryptJS.setBounds(20, 225, 340, 105);
		this.encryptJTA.setLineWrap(true); // 激活自动换行功能
		this.decryptJTA.setLineWrap(true); // 激活自动换行功能
		this.encryptJTA.setWrapStyleWord(true); // 激活断行不断字功能
		this.decryptJTA.setWrapStyleWord(true); // 激活断行不断字功能
		rightJP.add(this.encryptBtn);
		rightJP.add(this.decryptBtn);
		rightJP.add(this.encryptJS);
		rightJP.add(this.decryptJS);

		this.encryptBtn.addMouseListener(new EncryptionInnerAction());
		this.decryptBtn.addMouseListener(new EncryptionInnerAction());

		return rightJP;
	}

	// 返回JSplitPane对象
	private JSplitPane setJSplitPane() {
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.setLeftJPanel(),
				this.setRightJPanel());
		jSplitPane.setDividerLocation(100);
		jSplitPane.setEnabled(false);
		return jSplitPane;
	}

	//私有构造方法
	private MainJFrame() {
		this.setSize(500, 400);
		this.setTitle("JAVA课程设计之加解密系统");
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 设置窗口垂直居中显示
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 500) / 2, (height - 400) / 2);

		this.setContentPane(this.setJSplitPane());
		this.setDefault();
		this.setResizable(false);
	}

	// 默认设置
	private void setDefault() {
		this.base64Btn.setEnabled(false);
		this.flag = AlgorithmChoices.BASE64;
	}

	/**
	 * 线程安全的静态获取单例对象的方法
	 * 
	 * @return MainJFrame唯一对象
	 */
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

	public static void main(String[] args) {
		// 事件调度线程中执行创建窗口的方法
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainJFrame.getInstance().setVisible(true);
			}
		});
	}

}
