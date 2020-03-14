package com.nathanormond.gui.panels.services.buildprogrammer.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class ConsoleWindowPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel title;
	private JTextArea consoleOutputArea;
	private JScrollPane pane;
	
	public ConsoleWindowPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	
	@Override
	public void instantiateComponents() {
		title = new JLabel("OUTPUT");
		
		consoleOutputArea = new JTextArea();
		pane = new JScrollPane(consoleOutputArea);
		
		styleElements();
	}
	
	private void styleElements() { 
		consoleOutputArea.setEditable(false);
		
		Dimension d = new Dimension(450, 125);
		pane.setPreferredSize(d);
	}
	
	public void setConsoleText(String str) { 
		consoleOutputArea.setText(str);
	}
	
	@Override
	public void setActionCommands() {
		// not used set in individual panels
	}

	
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(title, 0, 0);
		addComponentToPanel(pane, 0, 1);
	}


	
	/***********************************************
	 * Action Events
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used as no actions	
	}

	
	/***********************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}


}
