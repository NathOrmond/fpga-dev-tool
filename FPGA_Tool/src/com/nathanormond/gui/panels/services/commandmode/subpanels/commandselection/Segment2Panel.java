package com.nathanormond.gui.panels.services.commandmode.subpanels.commandselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;


public class Segment2Panel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	public StartCommandPanel startListPanel;
	public StopCommandPanel stopListPanel;

	public Segment2Panel(ActionListener publicListener) {
		super(publicListener, true);
		// TODO Auto-generated constructor stub
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		//not used for super constructor
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(startListPanel.getPanel(), 0, 0);
		addComponentToPanel(stopListPanel.getPanel(), 1, 0);
	}
	
	@Override
	public void setActionCommands() {
		// not used
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		startListPanel = new StartCommandPanel(listener);
		stopListPanel = new StopCommandPanel(listener);
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
		this.setBorder(new TitledBorder("Command Selection"));
		return this;
	}
	
}