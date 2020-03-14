package com.nathanormond.gui.frames;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nathanormond.gui.components.menubar.FPGAMenuBar;


public class MainFrame extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private final String title = "FPGA Test Tool";
	private JPanel panel;

	/**
	 * Initialises frame, 
	 * Declares frames properties which will stay the same throughout the programme.
	 * @param panel
	 */
	public MainFrame(JPanel panel, ActionListener listener) {
		this.panel = panel;
		setTitle(this.title);
//		setLocationRelativeTo(null);
		setLocationByPlatform(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);				// Terminate prg on close button
		setVisible(true);
		setResizable(false);
		setMenuBar(listener);
		add(panel);
		pack();
	}
	
	private void setMenuBar(ActionListener listener){ 
		FPGAMenuBar menuBar = new FPGAMenuBar(listener);
		setJMenuBar(menuBar);
	}
	
	public void updateFrame(JPanel panel) { 
		removeFrame();
		this.panel = panel;
		add(panel);
		repaint();
		pack();
	}
	
	
	private void removeFrame() {
		remove(panel);
	}

	public void setPanel(JPanel panel) { 
		this.panel = panel;
	}

}