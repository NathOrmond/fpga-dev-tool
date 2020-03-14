package com.nathanormond.gui.panels.services.genericudppanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;


public class StopPanel extends AbstractPanel {


	private static final long serialVersionUID = 1L;
	public JButton stopButton;

	public StopPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		stopButton = new JButton("STOP");
		stopButton.setFont(getFont());
		stopButton.setSize(50,50);
		Color c = Color.RED;
		stopButton.setBackground(c);
		stopButton.setEnabled(false);
		
		stopButton.setToolTipText(stopToolTip());
	}
	
	private String stopToolTip() { 
		return "Send Stop CMD and stop test";
	}
	
	public Font getFont(){ 
		return new Font("Arial", Font.BOLD, 20);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(stopButton, 0, 0);
	}
	
	@Override
	public void setActionCommands() {
		stopButton.setActionCommand(ActionCommands.STOP);
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		stopButton.addActionListener(listener);
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		//not used
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//not used
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}
	
}
