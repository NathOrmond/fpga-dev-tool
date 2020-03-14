package com.nathanormond.gui.panels.services.tapontest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.services.genericudppanels.StartStopPanel;

public class TapOnTestPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	public StartStopPanel startStopPanel;
	
	public TapOnTestPanel(ActionListener publicListener) {
		super(publicListener, true);
	}
	
	/******************
	 * REFRESHMETHODS
	 */
	
	public void refreshPanel(ActionListener publicListener){ 
		removeAll();
		addComponentsToPanel();
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
		addComponentToPanel(startStopPanel.getPanel(), 0, 0);
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
		startStopPanel = new StartStopPanel(listener);
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
