package com.wyg.start;

import javax.swing.SwingUtilities;

public class Start {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainJFrame.getInstance().setVisible(true);
			}
		});
	}

}
