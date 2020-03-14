package com.nathanormond.gui.panels.services.commandmode.subpanels.transmissiontiming;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;


public class Segment1Panel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private NumCommandsPanel numCommandsPanel;
	private CommandDelayPanel commandDelayPanel;

	public Segment1Panel(ActionListener publicListener) {
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
		addComponentToPanel(numCommandsPanel.getPanel(), 0, 0);
		addComponentToPanel(commandDelayPanel.getPanel(), 1, 0);
	}
	
	@Override
	public void setActionCommands() {
		//not used as components are panels
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		numCommandsPanel = new NumCommandsPanel(listener);
		commandDelayPanel = new CommandDelayPanel(listener);
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