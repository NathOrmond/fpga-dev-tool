package com.nathanormond.gui.panels.services.genericudppanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class TimerPanel extends AbstractPanel{
	
	private static final long serialVersionUID = 1L;
	JLabel timerLabel;
	
	
	public TimerPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		//not used as super constructor set to true
	}

	@Override
	public void addComponentsToPanel() {

	}
	
	@Override
	public void setActionCommands() {
		//no action commands set here
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {

	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		//no local action listeners
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//no local actions performed
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("Tap On Mode Test : "));
		return this;
	}

}
