package com.nathanormond.gui.panels.generic.mainpanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

/**
 * 13/11/2018
 * @author Nathan Ormond
 */
public class ExpositionPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private PermanentConfigurationHeaderPanel configHeaderPanel;
	
	public ExpositionPanel(List<JPanel> panels, ActionListener publicListener) {
		super(panels, publicListener);
	}
	
	/**********************
	 * Initialisation
	 */

	@Override
	public void instantiateComponents() {
		// set in public action listeners so as public listener can be passed to constructor
	}

	@Override
	public void setActionCommands() {
		//not used
	}

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		configHeaderPanel = new PermanentConfigurationHeaderPanel(listener);
	}
	
	@Override
	public void addLocalActionListeners() {
		//not used
	}

	@Override
	public void addComponentsToPanel() {
		int x = 0, y = 0;
		addComponentToPanel(configHeaderPanel, x, y);
		
		for(JPanel panel : getPanels()) { 
			y++;										// adds panels iterating down in Y axis direction
			addComponentToPanel(panel, x, y);
		}
		
	}
	
	/**********************
	 * Methods
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//not used
	}
	
}
