package com.nathanormond.gui.panels.generic.configurationmenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

/**
 * 
 * 
 *  Override IPAddressPanel
 *  Override XMLPanel
 * 
 * @author chno
 *
 */
public class ConfigurationPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private OverrideXMLPanel overrideXMLPanel; 
	
	public ConfigurationPanel(ActionListener publicListener) {
		super(publicListener, true);
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
		addComponentToPanel(overrideXMLPanel.getPanel(), 0, 0);
	}
	
	@Override
	public void setActionCommands() {
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		 overrideXMLPanel = new OverrideXMLPanel(listener); 
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		//not used as no local listeners
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//not used as no local actions		
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}

}
