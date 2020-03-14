package com.nathanormond.gui.panels.services.buildprogrammer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class BuildProgrammerPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;


	public BuildProgrammerPanel(List<JPanel> panels, ActionListener publicListener) {
		super(panels, publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	
	@Override
	public void instantiateComponents() {
		// not used instantiated in composer
	}
	
	@Override
	public void setActionCommands() {
		// not used set in individual panels
	}

	
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used as set in individual panels
	}

	@Override
	public void addLocalActionListeners() {
		// not used as set in individual panels
	}

	@Override
	public void addComponentsToPanel() {
		int count = 0;
		for(JPanel panel: getPanels()) { 
			addComponentToPanel(panel, 0, count);
			count++;
		}
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
