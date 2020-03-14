package com.nathanormond.gui.panels.services.genericudppanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;


public class StartPanel extends AbstractPanel {


	private static final long serialVersionUID = 1L;
	public JButton startButton;

	public StartPanel(ActionListener publicListener) {
		super(publicListener);
		// TODO Auto-generated constructor stub
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		startButton = new JButton("START");
		startButton.setFont(getFont());
		startButton.setSize(50,50);
		Color c = Color.GREEN;
		startButton.setBackground(c);
		startButton.setToolTipText(startToolTip());
	}
	
	private String startToolTip() { 
		return "Send Start CMD and start test";
	}
	
	public Font getFont(){ 
		return new Font("Arial", Font.BOLD, 20);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(startButton, 0, 0);
	}
	
	@Override
	public void setActionCommands() {
		startButton.setActionCommand(ActionCommands.START);
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		startButton.addActionListener(listener);
		
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