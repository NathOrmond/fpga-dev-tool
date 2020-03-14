package com.nathanormond.gui.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OutputTerminalFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public OutputTerminalFrame(JPanel panel) {
		setTitle("Live Output Terminal");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(true);
		add(panel);
		pack();
	}
	
}