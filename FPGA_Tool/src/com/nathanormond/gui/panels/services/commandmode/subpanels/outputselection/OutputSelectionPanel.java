package com.nathanormond.gui.panels.services.commandmode.subpanels.outputselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class OutputSelectionPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	JLabel title;
	private OutputButtonsPanel outputButtons;
	
	public OutputSelectionPanel(ActionListener publicListener) {
		super(publicListener);
		// TODO Auto-generated constructor stub
	}

	/*************************************************
	 * Instantiation
	 */
	
	@Override
	public void instantiateComponents() {
		title = new JLabel("OUTPUT SELECTION");
		outputButtons = new OutputButtonsPanel(this);
		styleElements();
	}
	
	private void styleElements() { 

	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
	}
	
	@Override
	public void setActionCommands() {
		// not used
	}

	@Override
	public void addLocalActionListeners() {

	}
	
	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(title, 0, 1);
		addComponentToPanel(outputButtons, 0, 2);
	}
	
	/*************************************************
	 * Actions
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// not used as radio button group
	}
	
	
	/*************************************************
	 * GETTERS & SETTERS
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}

}
